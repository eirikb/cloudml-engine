import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers

@RunWith(classOf[JUnitRunner])
class HelloWorldSpec extends Spec with ShouldMatchers {
  describe("Testing tests like a boss") {

    it("should pop values in last-in-first-out order") {
        println("Oh hai")
        assert(1 == 1)
    }
  }
}
