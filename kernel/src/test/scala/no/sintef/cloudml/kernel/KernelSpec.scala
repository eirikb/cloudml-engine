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
      val s = """{"name":"test", "nodes": [{"name":"Node 1"},{"name": "Node 2"}]}"""
      kernel.create(s)

      assert(1 === 1)
    }
  }
}
