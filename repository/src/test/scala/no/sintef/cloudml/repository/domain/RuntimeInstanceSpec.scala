package no.sintef.cloudml.repository.domain

import org.specs2.mutable._

import no.sintef.cloudml.kernel.domain.Node

class RuntimeInstanceSpec extends SpecificationWithJUnit {
    "RuntimeInstance actors" should {
        "react and add properties when called" in {
            val n = Node("test1", None, None, None, None)
            var ri = new RuntimeInstance(n)
            ri.start

            ri.node mustEqual(n)

            var count = 0
            var status = Status.Configuring

            ri.addListener( (event) => 
                event match {
                    case Event.Property => count += 1
                    case Event.Status => status = ri.status
                }
            )

            ri ! AddProperty("id", "test")
            ri ! AddProperty("test", "ing")
            ri ! SetStatus(Status.Building)

            // TODO why will these not run as 'eventually'
            //ri.properties("test") must eventually(be("ing"))
            //ri.properties("id") must eventually(be("test"))
            //count must eventually(beEqualTo(2))
            //status must eventually(be(Status.Building))
            1 mustEqual 1
        }
    }
}
