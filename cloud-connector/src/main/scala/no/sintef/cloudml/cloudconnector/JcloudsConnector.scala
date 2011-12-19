package no.sintef.cloudml.cloudconnector

import org.jclouds.compute._
import org.jclouds.aws.ec2.compute._

import org.jclouds.ec2.domain.InstanceType

import no.sintef.cloudml.repository.domain._
import no.sintef.cloudml.kernel.domain._

class JcloudsConnector extends CloudConnector {

    def createInstance(account: Account, instance: Instance) {

        val authKeys = account.authKeys

        val context = new ComputeServiceContextFactory().createContext(account.provider, authKeys.accessKey, authKeys.secretKey)
        val computeService = context.getComputeService()
        val template = computeService.templateBuilder().hardwareId(instance.size match {
            case "Small" => InstanceType.T1_MICRO
            case "Medium" => InstanceType.M1_SMALL
            case "Large" => InstanceType.M1_LARGE
            case _ => InstanceType.M1_SMALL
        }).build()
        context.getComputeService().createNodesInGroup("webserver", 1, template)
    }
}
