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
            credential.identity, credential.credential)
        val computeService = context.getComputeService()

        instances.map(instance => {
            val template = computeService.templateBuilder().minRam(instance.minRam).build()
            val nodes = context.getComputeService().createNodesInGroup("webserver", 1, template).toSet
            val node = nodes.head
            new RuntimeInstance( node.getId(), node.getPrivateAddresses().toSet.head, 
                node.getPublicAddresses().toSet.head, instance)
        })
    }
}
