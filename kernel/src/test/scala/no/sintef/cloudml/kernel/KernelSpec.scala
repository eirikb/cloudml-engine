package no.sintef.cloudml.kernel

import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers

@RunWith(classOf[JUnitRunner])
class KernelSpec extends Spec with ShouldMatchers {
    describe("Testing like a boss") {

        it("testing testing") {
            assert(1 === 1)
        }
    }
}
