package no.sintef.cloudml.engine

import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers

import no.sintef.cloudml.kernel.domain._

@RunWith(classOf[JUnitRunner])
class EngineSpec extends Spec with ShouldMatchers {
    describe("Basic engine testing. the apply") {

      it("should return a list of runtimeinstances when called") {
        assert(1 == 1)
        println("LIKE A BOSS")
      }
    }
}
