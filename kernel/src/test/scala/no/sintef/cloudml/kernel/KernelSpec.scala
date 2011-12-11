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
            val s = """{"nodes": [{},{}]}"""
            val t = Kernel.deserializeTemplate(s)

            assert(t.nodes.length == 2)
            assert(t == new Template(List(new Node(), new Node())))
        }

        it("desrialilzes accounts") {
            val s = """{"provider":"aws-ec2", "authKeys":{"accessKey":"accessTest", "secretKey": "secretTest"}}"""
            val a = Kernel.deserializeAccount(s)

            assert(a.provider == "aws-ec2")
            assert(a.authKeys.accessKey == "accessTest")
            assert(a.authKeys.secretKey == "secretTest")
            assert(a == Account("aws-ec2", AuthKeys("accessTest", "secretTest")))
        }
    }
}
