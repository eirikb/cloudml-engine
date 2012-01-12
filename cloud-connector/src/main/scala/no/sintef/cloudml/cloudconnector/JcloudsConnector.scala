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
import org.jclouds.aws.ec2.compute._
import org.jclouds.ec2.domain.InstanceType

import no.sintef.cloudml.repository.domain._
import no.sintef.cloudml.kernel.domain._

import scala.collection.JavaConversions._

class JcloudsConnector extends CloudConnector {

    def createInstances(account: Account, instances: List[Instance]): List[RuntimeInstance]  = {

        val context = new ComputeServiceContextFactory().createContext(account.provider, 
            account.identity, account.credential)
        val computeService = context.getComputeService()

        //val profiles = computeService.listHardwareProfiles()

         //   profiles.foreach(p =>
          //   println( " " + p + "\n"))



        instances.map { instance => {
            val templateBuilder = computeService.templateBuilder().minRam(instance.minRam).minCores(instance.minCores)

            if (instance.minDisk > 0) {
                if (account.provider == "aws-ec2") {
                    println("OH SNAP")
                } else {
                    val profiles = computeService.listHardwareProfiles.toList

                    def volumeSum(l: List[Volume]) : Int = { 
                        l.map(_.getSize).reduceLeft(_+_).toInt
                    }

                    val found = profiles.filter({ p =>
                        val total = p.getVolumes.map(_.getSize).reduceLeft(_+_).toInt
                        total >= instance.minDisk
                    })

                val f = found.sort((a,b) => {volumeSum(a.getVolumes.toList) > volumeSum(b.getVolumes.toList)}).last

                    println("Found: " + found)
                    println("Found SPE: " + f)
                }
            }


            //val nodes = context.getComputeService().createNodesInGroup("webserver", 1, template).toSet
            //val node = nodes.head
            //new RuntimeInstance( node.getId(), node.getPrivateAddresses().toSet.head, 
             //   node.getPublicAddresses().toSet.head, instance)
             null
            }
        }
    }
}
