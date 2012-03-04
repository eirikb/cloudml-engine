package no.sintef.cloudml.repository

import org.specs2.mutable._

import no.sintef.cloudml.kernel.domain._
import no.sintef.cloudml.repository.domain._

class RepositorySpec extends SpecificationWithJUnit {
    "Nodes" should {
        "map to instances" in {
            val instances = Repository.mapping(null, List(
                Template("test", List(
                    Node("test1", None, None, None, None),
                    Node("test2", Some(1000), None, None, None),
                    Node("test3", Some(2000), Some(5), None, None),
                    Node("test4", Some(2000), Some(5), Some(1000), None)))))

            instances mustEqual(List(
                Instance("test1", "test", 0, 1, 0, ""),
                Instance("test2", "test", 1000, 1, 0, ""),
                Instance("test3", "test", 2000, 5, 0, ""),
                Instance("test4", "test", 2000, 5, 1000, "")))
        }

        "have same fields as instance, except templateName" in {
            val nFields = classOf[Node].getDeclaredFields.map(_.getName)
            // Remove 'templateName' from field list
            val iFields = classOf[Instance].getDeclaredFields.map(_.getName).
              filter(!_.contains("template"))

            println(iFields)
            nFields mustEqual iFields
        }
    }
}
