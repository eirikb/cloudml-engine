package no.sintef.cloudml.cloudconnector

import org.jclouds.compute._
import org.jclouds.aws.ec2.compute._

import no.sintef.cloudml.repository.domain._
import no.sintef.cloudml.kernel.domain._

class JcloudsConnector extends CloudConnector {

    def createInstance(account: Account, instance: Instance) {

        val authKeys = account.authKeys

        val context = new ComputeServiceContextFactory().createContext(account.provider, authKeys.accessKey, authKeys.secretKey)
        context.getComputeService().createNodesInGroup("webserver", 1)
    }
}
