package no.sintef.cloudml

import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers

import no.sintef.cloudml.cloudconnector._

@RunWith(classOf[JUnitRunner])
class CloudsConnectorSpec extends Spec with ShouldMatchers {
    describe("Testing CloudConnector") {

        it("should return jscloud when asked for it") {
            var connector = CloudConnector("jscloud")
            assert(connector.getClass === classOf[JcloudsConnector])
        }

        it("should return jscloud when anything is specified") {
            var connector = CloudConnector("some random string")
            assert(connector.getClass === classOf[JcloudsConnector])
        }
    }
}
