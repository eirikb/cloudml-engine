package no.sintef.cloudml.cloudconnector

import org.jclouds.compute._
import org.jclouds.aws.ec2.compute._

import no.sintef.cloudml.repository._
import no.sintef.cloudml._

class JcloudsConnector extends CloudConnector {

  def createInstance(instance: Instance) {

    val context = new ComputeServiceContextFactory().createContext("aws-ec2", instance.authKeys.accessKey, instance.authKeys.secretKey)

    val template = context.getComputeService().templateOptions().as(classOf[AWSEC2TemplateOptions]).securityGroups(instance.group).keyPair(instance.keyPair)

    println("Here should tempalte " + template + " be built, accessKeys: " + instance.authKeys)
    //context.getComputeService().createNodesInGroup("webserver", 2, template)
  }
}
