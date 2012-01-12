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
            val instances = Repository.mapping(null, List(Template(List(Node("test1"), Node("test2", 1000), Node("test3", 2000, 5), Node("test4", 2000, 5, 1000)))))

            instances should equal(List(Instance("test1", 0, 1, 0), Instance("test2", 1000, 1, 0), Instance("test3", 2000, 5, 0), Instance("test4", 2000, 5, 1000)))
        }

        it("should atm be complete equality of nodes and instances") {

            val nodes = List(Node("test1"), Node("test2", 1000), Node("test3", 2000, 5))
            val instances = Repository.mapping(null, List(Template(nodes)))

            nodes.zipWithIndex.foreach { case (node, i) =>
                val instance = instances(i)
                val fields = node.getClass.getDeclaredFields
                fields.foreach { field =>
                    val nMethod = node.getClass.getDeclaredMethod(field.getName)
                    val nValue = nMethod.invoke(node)
                    val iMethod = instance.getClass.getDeclaredMethod(field.getName)
                    val iValue = iMethod.invoke(instance)
                    nValue should equal(iValue)
                }
            }
        }
    }
}
