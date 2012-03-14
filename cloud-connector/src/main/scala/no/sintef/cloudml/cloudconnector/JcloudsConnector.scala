/**
 * This file is part of CloudML
 *
 * Copyright (C) 2012-  SINTEF ICT
 * Contact: Eirik Brandtzæg <eirikb@eirikb.no>
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

import org.jclouds.compute.ComputeServiceContextFactory
import org.jclouds.compute.domain.Volume
import org.jclouds.compute.domain.Hardware
import org.jclouds.loadbalancer._
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

    private def getLoadBalancerContext(): LoadBalancerService = {
        val context = new LoadBalancerServiceContextFactory().createContext(account.provider, 
            account.identity, account.credential)
        context.getLoadBalancerService()
    }

    def findHardwareByDisk(profiles: List[Hardware], minDisk: Int): Hardware = {
        def volumeSum(l: List[Volume]): Int = { 
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

    override def createInstances(template: Template): List[RuntimeInstance] = {
        val client = auth()

        val runtimeInstanceMap = template.nodes.map ( node => {
            val runtimeInstance = new RuntimeInstance(node)
            runtimeInstance.start

            val templateBuilder = client.templateBuilder()
            if (node.minRam.get > 0) 
                templateBuilder.minRam(node.minRam.get)
            if (node.minCores.get > 0) 
                templateBuilder.minCores(node.minCores.get)
            if (!node.locationId.getOrElse("").isEmpty)
                templateBuilder.locationId(node.locationId.get)

            // Pick the node with enough disk available
            if (node.minDisk.get > 0 && account.provider != "aws-ec2") {
                val profiles = client.listHardwareProfiles.toList
                val hw = findHardwareByDisk(profiles, node.minDisk.get)
                templateBuilder.hardwareId(hw.getId)
            }

            val cloudTemplate = templateBuilder.build()

            // AWS EC2 support ELB and can dynamically allocate disk
            // Must be run after template is built
            if (node.minDisk.get > 0 && account.provider == "aws-ec2") {
                cloudTemplate.getOptions().as(classOf[EC2TemplateOptions]).
                    mapNewVolumeToDeviceName("/dev/sdm", node.minDisk.get, true)
            }

            cloudTemplate.getOptions().blockUntilRunning(true)

            runtimeInstance ! AddProperty("imageId", cloudTemplate.getImage.getId())
            runtimeInstance ! AddProperty("location", cloudTemplate.getLocation.getId())

            runtimeInstance -> cloudTemplate
        }).toMap

        Futures.future {
            var counter = runtimeInstanceMap.size()

            runtimeInstanceMap.foreach { case (runtimeInstance, cloudTemplate) => {
                runtimeInstance ! SetStatus(Status.Building)

                val nodes = client.createNodesInGroup("webserver", 1, cloudTemplate).toSet
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

                counter -= 1
                if (counter == 0 && template.loadBalancer.isDefined) {
                    val lb = template.loadBalancer.get
                    val loadBalancerContext = getLoadBalancerContext()
                    val balancer = loadBalancerContext.createLoadBalancerInLocation(
                            null, lb.name, lb.protocol, lb.loadBalancerPort, 
                            lb.instancePort, nodes)
                }

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
}
