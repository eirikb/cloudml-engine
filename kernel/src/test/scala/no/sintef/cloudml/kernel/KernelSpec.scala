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

    it("wires up quite well") {
      implicit val formats = net.liftweb.json.DefaultFormats
      val s = """{"name":"test", "nodes": [{"name":"Node 1"},{"name": "Node 2"}]}"""
      val t = Kernel.deserialize(s)

      assert(t.nodes.length == 2)
      assert(t == new Template("test", List(new Node("Node 1"), new Node("Node 2"))))
    }
  }
}
