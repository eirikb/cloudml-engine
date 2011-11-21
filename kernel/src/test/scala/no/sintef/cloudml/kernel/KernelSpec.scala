package no.sintef.cloudml.kernel

import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers

import net.liftweb.json._
import net.liftweb.json.JsonAST._
import net.liftweb.json.Serialization.{read, write => swrite}

@RunWith(classOf[JUnitRunner])
class KernelSpec extends Spec with ShouldMatchers {
  describe("Testing like a boss") {

    it("testing testing") {
      implicit val formats = net.liftweb.json.DefaultFormats
      val node = new Node()

        //      val s = """{"employees":[{"fname":"Bob","lname":"Hope"},{"fname":"Bob","lname":"Smith"}]}"""
      val s = """{"name":"test"}"""

      var t = new Template("test")
      //    println(swrite(t))
      //println(net.liftweb.json.Serialization.read[Template](s))

      println("...")
      println(s)
      println(t)
      println(net.liftweb.json.Serialization.read[Template](s))
      println("omg")
      println("Hello world")
      println("...")

      assert(1 === 1)
    }
  }
}
