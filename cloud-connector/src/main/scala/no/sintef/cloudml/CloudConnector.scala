package no.sintef.cloudml

import no.sintef.cloudml.repository._
import no.sintef.cloudml.cloudconnector._

trait CloudConnector {
    def createInstance(authKeys: AuthKeys, instance: Instance)
}

object CloudConnector {

    def apply(instanceType: String): CloudConnector = instanceType match {
        case "jclouds" =>
            new JcloudsConnector();
        case _ =>
            new JcloudsConnector();
    }
}
