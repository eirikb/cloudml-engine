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
import org.jclouds.aws.ec2.compute._

import org.jclouds.ec2.domain.InstanceType

import no.sintef.cloudml.repository.domain._
import no.sintef.cloudml.kernel.domain._

import scala.collection.JavaConversions._

class JcloudsConnector extends CloudConnector {

    def createInstances(account: Account, instances: List[Instance]): List[RuntimeInstance]  = {

        val credential = account.credential

        val context = new ComputeServiceContextFactory().createContext(account.provider, 
            account.identity, account.credential)
        val computeService = context.getComputeService()

        instances.map(instance => {
            val template = computeService.templateBuilder().minRam(instance.minRam).minCores(instance.minCores).build()
            val nodes = context.getComputeService().createNodesInGroup("webserver", 1, template).toSet
            val node = nodes.head
            new RuntimeInstance( node.getId(), node.getPrivateAddresses().toSet.head, 
                node.getPublicAddresses().toSet.head, instance)
        })
    }
}
