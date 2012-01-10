package no.sintef.cloudml.repository

import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers

import no.sintef.cloudml.kernel.domain._
import no.sintef.cloudml.repository.domain._

@RunWith(classOf[JUnitRunner])
class RepositorySpec extends Spec with ShouldMatchers {
    describe("Mapping") {
        it("should map nodes to instances") {
            val instances = Repository.mapping(null, List(Template(List(Node("test1"), Node("test2", 1000), Node("test3", 2000, 5)))))

            instances should equal(List(Instance("test1"), Instance("test2", 1000), Instance("test3", 2000, 5)))

            instances should equal(List(Instance("test1", 0, 1), Instance("test2", 1000, 1), Instance("test3", 2000, 5)))
        }
    }
}
