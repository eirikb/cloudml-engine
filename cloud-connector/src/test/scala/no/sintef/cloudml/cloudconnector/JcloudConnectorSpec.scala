package no.sintef.cloudml.cloudconnector

import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers


@RunWith(classOf[JUnitRunner])
class JcloudsConnectorSpec extends Spec with ShouldMatchers {
    describe("Testing like a boss") {

        it("testing testing") {
            var connector = new JcloudsConnector();
            connector.createInstances();
        }
    }
}
