package no.sintef.cloudml.repository

import org.specs2.mutable._

import no.sintef.cloudml.kernel.domain._
import no.sintef.cloudml.repository.domain._

class RepositorySpec extends SpecificationWithJUnit {
    "Nodes" should {
        "map to instances" in {
            val instances = Repository.mapping(null, List(Template(List(Node("test1"), 
                Node("test2", 1000), Node("test3", 2000, 5), Node("test4", 2000, 5, 1000)))))

            instances mustEqual(List(Instance("test1", 0, 1, 0), Instance("test2", 1000, 1, 0), 
                Instance("test3", 2000, 5, 0), Instance("test4", 2000, 5, 1000)))
        }

        "atm be complete equality of nodes and instances" in {
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
                    nValue mustEqual(iValue)
                }
            }
            nodes.length mustEqual(instances.length)
        }
    }
}
