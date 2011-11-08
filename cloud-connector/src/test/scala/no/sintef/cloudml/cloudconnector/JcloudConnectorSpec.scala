package no.sintef.cloudml.cloudconnector

import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers


import no.sintef.cloudml.repository._

@RunWith(classOf[JUnitRunner])
class JcloudsConnectorSpec extends Spec with ShouldMatchers {
    describe("Testing like a boss") {

        it("testing testing") {
            var connector = new JcloudsConnector();
            var instance = new Instance("", "dev", new AuthKeys("...", "..."))
            //connector.createInstances(instance);
            assert(1 === 1)
        }
    }
}
