/**
 * This file is part of CloudML
 *
 * Copyright (C) 2012-  SINTEF ICT
 * Contact: Eirik Brandtzæg <eirikb@eirikb.no>
 *
 * Module: cloud-connector
 *
 * CloudML is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * CloudML is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with CloudML. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package no.sintef.cloudml.cloudconnector

import org.jclouds.compute._
import org.jclouds.compute.domain.Volume
import org.jclouds.compute.domain.Hardware
import org.jclouds.aws.ec2.compute._
import org.jclouds.ec2.domain.InstanceType
import org.jclouds.ec2.compute.options.EC2TemplateOptions

import no.sintef.cloudml.repository.domain._
import no.sintef.cloudml.kernel.domain._

import scala.collection.JavaConversions._
import scala.actors.Futures

class JcloudsConnector(account: Account) extends CloudConnector {

    private def auth() = {
        val context = new ComputeServiceContextFactory().createContext(account.provider, 
            account.identity, account.credential)
        context.getComputeService()
    }

    def findHardwareByDisk(profiles: List[Hardware], minDisk: Int): Hardware = {
        def volumeSum(l: List[Volume]) : Int = { 
            l.map(_.getSize).reduceLeft(_+_).toInt
        }

        val filtered = profiles.filter(p => volumeSum(p.getVolumes.toList) >= minDisk)

        if (filtered.size > 0) {
            filtered.sortWith((a, b) => 
                volumeSum(a.getVolumes.toList) < volumeSum(b.getVolumes.toList)).head
        } else {
            throw new RuntimeException("minDisk too large")
        }
    }

    override def createInstances(instances: List[Instance]): List[RuntimeInstance]  = {
        val client = auth()

        val runtimeInstanceMap = instances.map ( instance => {
            val runtimeInstance = new RuntimeInstance(instance)
            runtimeInstance.start

            val templateBuilder = client.templateBuilder().minRam(instance.minRam).
                minCores(instance.minCores).locationId(instance.locationId)

            if (instance.minDisk > 0 && account.provider != "aws-ec2") {
                val profiles = client.listHardwareProfiles.toList
                val hw = findHardwareByDisk(profiles, instance.minDisk)
                templateBuilder.hardwareId(hw.getId)
            }

            val template = templateBuilder.build()
            template .getOptions().blockUntilRunning(true)

            if (instance.minDisk > 0 && account.provider == "aws-ec2") {
                template.getOptions().as(classOf[EC2TemplateOptions]).
                    mapNewVolumeToDeviceName("/dev/sdm", instance.minDisk, true)
            }

            runtimeInstance ! AddProperty("test", "tast")
            runtimeInstance ! AddProperty("imageId", template.getImage.getId())
            runtimeInstance ! AddProperty("location", template.getLocation.getId())

            runtimeInstance -> template
        }).toMap

        Futures.future {
            runtimeInstanceMap.foreach { case (runtimeInstance, template) => {
                runtimeInstance ! SetStatus(Status.Building)

                val nodes = client.createNodesInGroup("webserver", 1, template).toSet
                val node = nodes.head
                runtimeInstance ! AddProperty("id", node.getId())
                runtimeInstance ! AddProperty("provider", node.getProviderId())
                runtimeInstance ! AddProperty("name", node.getName())
                runtimeInstance ! AddProperty("location", node.getLocation().toString)

                runtimeInstance ! SetStatus(Status.Starting)

                val metadata = client.getNodeMetadata(node.getId());

                runtimeInstance ! AddProperty("privateAddresses", metadata.getPrivateAddresses().mkString(","))
                runtimeInstance ! AddProperty("publicAddresses", metadata.getPublicAddresses().mkString(","))
                runtimeInstance ! AddProperty("id", metadata.getId())

                runtimeInstance ! SetStatus(Status.Started)
                runtimeInstance
            }}
        }
        runtimeInstanceMap.keys.toList
    }

    override def destroyInstance(id: String) {
        val client = auth()
        client.destroyNode(id)
    }

    override def runScript(id: String, script: String) {
        val client = auth()
        client.runScriptOnNode(id, script)
    }
}
