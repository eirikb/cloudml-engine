package no.sintef.cloudml.cloudconnector

import org.jclouds.compute._
import org.jclouds.aws.ec2.compute._

import no.sintef.cloudml.repository._
import no.sintef.cloudml._

class JcloudsConnector extends CloudConnector {

    def createInstance(account: Account, instance: Instance) {

        val authKeys = account.authKeys
        println("Here should instance" + instance + " be built, accessKeys: " + authKeys)

        //val context = new ComputeServiceContextFactory().createContext("aws-ec2", authKeys.accessKey, authKeys.secretKey)

        //val template = context.getComputeService().templateOptions().as(classOf[AWSEC2TemplateOptions]).securityGroups(instance.group).keyPair(instance.keyPair)

        //context.getComputeService().createNodesInGroup("webserver", 2, template)
    }
}
