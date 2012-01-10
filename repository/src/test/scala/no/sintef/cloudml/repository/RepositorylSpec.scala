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
            val instances = Repository.mapping(null, Template(List(Node("test1"), Node("test2", 1000), Node("test3", 2000, 5)))
            println(instances)
        }
    }
        
        

/*
        it("AWS should be mapped directly") {
            val account = new Account("aws-ec2", null)
            val template = new Template(List(new Node(), new Node("Small")))
            val instances = Repository.mapping(account, List(template))
            assert(instances.length == 2)
            assert(instances.head == new AWSInstance("Medium"))
            assert(instances.last == new AWSInstance("Small"))
          }
    
        it("Rackspace should be mapped to numbers (flavor)") {
            val account = new Account("cloudservers", null)
            val template = new Template(List(new Node(), new Node("Small"), new Node("Large")))
            val instances = Repository.mapping(account, List(template))
            assert(instances.length == 3)
            assert(instances.head == new RackspaceInstance(2))
            assert(instances(1) == new RackspaceInstance(1))
            assert(instances.last == new RackspaceInstance(3))
        }
*/
    }
}
