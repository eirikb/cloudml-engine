package no.sintef.cloudml.cloudconnector

import no.sintef.cloudml.repository.domain._
import no.sintef.cloudml.kernel.domain._

trait CloudConnector {
    def createInstances(account: Account, instance: List[Instance]): List[RuntimeInstance]
}

object CloudConnector {

    def apply(instanceType: String): CloudConnector = instanceType match {
        case "jclouds" =>
            new JcloudsConnector();
        case _ =>
            new JcloudsConnector();
    }
}
