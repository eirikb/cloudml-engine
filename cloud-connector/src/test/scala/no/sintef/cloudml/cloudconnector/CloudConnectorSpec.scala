package no.sintef.cloudml

import org.specs2.mutable._

import no.sintef.cloudml.cloudconnector._

class CloudsConnectorSpec extends SpecificationWithJUnit {
    "CloudConnector" should {
        "return jscloud when asked for it" in {
            val connector = CloudConnector(null, "jscloud")
            connector must beAnInstanceOf[JcloudsConnector]
        }

        "return jscloud when anything is specified" in {
            val connector = CloudConnector(null, "some random string")
            connector must beAnInstanceOf[JcloudsConnector]
        }
    }
}
