package no.sintef.cloudml

import no.sintef.cloudml.repository._
import no.sintef.cloudml.cloudconnector._

trait CloudConnector {
    def createInstance(instance: Instance)
}

object CloudConnector {
    def apply(instanceType: String): CloudConnector = {
      Map(
        "jclouds" -> (() => new JcloudsConnector())
      ).get(instanceType).map(f => f()).getOrElse(new JcloudsConnector())
   }
}
