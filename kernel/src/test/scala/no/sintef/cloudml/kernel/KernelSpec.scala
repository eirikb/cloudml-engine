package no.sintef.cloudml.kernel

import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers

import no.sintef.cloudml.kernel.domain._

@RunWith(classOf[JUnitRunner])
class KernelSpec extends Spec with ShouldMatchers {
    describe("Testing like a boss") {

        it("desrialilzes templates") {
            val s = """{"name":"test", "nodes": [{"name":"Node 1"},{"name": "Node 2"}]}"""
            val t = Kernel.deserializeTemplate(s)

            assert(t.nodes.length == 2)
            assert(t == new Template("test", List(new Node("Node 1"), new Node("Node 2"))))
        }

        it("desrialilzes accounts") {
            val s = """{"name":"test", "authKeys":{"accessKey":"accessTest", "secretKey": "secretTest"}}"""
            val a = Kernel.deserializeAccount(s)

            assert(a.name == "test")
            assert(a.authKeys.accessKey == "accessTest")
            assert(a.authKeys.secretKey == "secretTest")
            assert(a == Account("test", AuthKeys("accessTest", "secretTest")))
        }
    }
}
