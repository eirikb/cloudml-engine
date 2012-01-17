package no.sintef.cloudml.kernel

//import org.scalatest.junit.JUnitRunner
//import org.junit.runner.RunWith
//import org.scalatest.Spec
//import org.scalatest.matchers.ShouldMatchers
//import org.specs2.mutable._
//import org.scalatest.junit.JUnitRunner
//import org.junit.runner.RunWith
import org.specs2.mutable._

import no.sintef.cloudml.kernel.domain._

//@RunWith(classOf[JUnitRunner])
class KernelSpec extends SpecificationWithJUnit {
    "Template in JSON format" should {
        "serialize to internal format" in {
            val s = """{
                "nodes": [{
                    "name": "test1", 
                    "minRam": 0, 
                    "minCores": 1, 
                    "minDisk": 0
                },{
                    "name": "test2", 
                    "minRam": 1000, 
                    "minCores": 2, 
                    "minDisk": 2000
                }]}"""
            val t = Kernel.deserializeTemplate(s)

            t.nodes.length === 2
            t mustEqual(new Template(List(new Node("test1"), new Node("test2", 1000, 2, 2000))))
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
