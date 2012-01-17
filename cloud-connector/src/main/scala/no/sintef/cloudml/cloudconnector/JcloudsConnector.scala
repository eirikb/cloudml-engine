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

class JcloudsConnector extends CloudConnector {

    def findHardwareByDisk(profiles: List[Hardware], minDisk: Int): Hardware = {
        def volumeSum(l: List[Volume]) : Int = { 
            l.map(_.getSize).reduceLeft(_+_).toInt
        }

        val filtered = profiles.filter(p => volumeSum(p.getVolumes.toList) >= minDisk)

        if (filtered.size > 0) {
            filtered.sort((a,b) => 
                volumeSum(a.getVolumes.toList) < volumeSum(b.getVolumes.toList)).first
        } else {
            throw new RuntimeException("minDisk too large")
        }
    }

    def createInstances(account: Account, instances: List[Instance]): List[RuntimeInstance]  = {

        val instanceMap = instances.map(instance => instance -> new RuntimeInstance(instance))
        val context = new ComputeServiceContextFactory().createContext(account.provider, 
            account.identity, account.credential)
        val computeService = context.getComputeService()

        instances.map ( instance => {
            val runtimeInstance = new RuntimeInstance(instance)

            val templateBuilder = computeService.templateBuilder().minRam(instance.minRam).minCores(instance.minCores)

            if (instance.minDisk > 0 && account.provider != "aws-ec2") {
                val profiles = computeService.listHardwareProfiles.toList
                val hw = findHardwareByDisk(profiles, instance.minDisk)
                templateBuilder.hardwareId(hw.getId)
            }

            val template = templateBuilder.build()

            if (instance.minDisk > 0 && account.provider == "aws-ec2") {
                template.getOptions().as(classOf[EC2TemplateOptions]).
                    mapNewVolumeToDeviceName("/dev/sdm", instance.minDisk, true)
            }

            Futures.future {
                val nodes = context.getComputeService().createNodesInGroup("webserver", 1, template).toSet
                val node = nodes.head
                runtimeInstance.id = node.getId()
            }
            runtimeInstance
        })
    }
}
