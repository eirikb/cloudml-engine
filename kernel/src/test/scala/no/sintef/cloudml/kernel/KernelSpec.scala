package no.sintef.cloudml.kernel

import org.specs2.mutable._

import no.sintef.cloudml.kernel.domain._

class KernelSpec extends SpecificationWithJUnit {
    "Template in JSON format" should {
        "serialize to internal format" in {
            val s = """{
                "nodes": [{
                    "name": "test1"
                },{
                    "name": "test2", 
                    "minRam": 1000, 
                    "minCores": 2, 
                    "minDisk": 2000,
                    "locationId": "USA"
                }]}"""
            val t = Kernel.deserializeTemplate(s)

            t.nodes.length === 2
            t mustEqual(new Template(List(new Node("test1", None, None, None, None), new Node("test2", Some(1000), Some(2), Some(2000), Some("USA")))))
            1 mustEqual 1
        }
     }

     "Account in JSON format" should {
        "serialize to internal format" in {
            val s = """{
                "provider": "aws-ec2", 
                "identity": "identityTest", 
                "credential": "credentialTest"
                }"""
            val a = Kernel.deserializeAccount(s)

                a.provider mustEqual("aws-ec2")
                a.identity mustEqual("identityTest")
                a.credential mustEqual("credentialTest")
                a mustEqual(Account("aws-ec2", "identityTest", "credentialTest"))
        }
     }
 }
