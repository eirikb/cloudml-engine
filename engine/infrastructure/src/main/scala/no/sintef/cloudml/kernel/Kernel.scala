package no.sintef.cloudml.kernel

import net.liftweb.json._
import net.liftweb.json.JsonAST._
import net.liftweb.json.Serialization.read

object Kernel {

  def create(templateJson: String) {
    println(templateJson + " JSON!")
    println(deserialize(templateJson))
  }

  def deserialize(in: String): Template = {
    implicit val formats = DefaultFormats
    read[Template](in)
  }
}
