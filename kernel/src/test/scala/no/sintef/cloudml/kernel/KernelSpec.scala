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
            val s = """{
                "nodes": [{
                    "name": "test1", "minRam": 0, "minCores": 1, "minDisk": 0
                },{
                    "name": "test2", "minRam": 1000, "minCores": 2, "minDisk": 2000
                }]}"""
            val t = Kernel.deserializeTemplate(s)

            assert(t.nodes.length == 2)
            assert(t == new Template(List(new Node("test1"), new Node("test2", 1000, 2, 2000))))
        }

        it("desrialilzes accounts") {
            val s = """{
                "provider": "aws-ec2", 
                "identity": "accessTest", 
                "credential": "secretTest"
                }"""
            val a = Kernel.deserializeAccount(s)

            assert(a.provider == "aws-ec2")
            assert(a.identity == "accessTest")
            assert(a.credential == "secretTest")
            assert(a == Account("aws-ec2", "accessTest", "secretTest"))
        }
    }
}
