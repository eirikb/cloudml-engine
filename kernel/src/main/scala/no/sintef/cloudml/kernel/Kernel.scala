package no.sintef.cloudml.kernel

import net.liftweb.json._
import net.liftweb.json.JsonAST._
import net.liftweb.json.Serialization.read

case class Employee(fname: String, lname: String) { }
case class Employees(employees: List[Employee]) { }

object Kernel {

  def create(templateJson: String) :String= {
  println(templateJson + " JSON!")
    val e = deserialize(templateJson)
    println(e + " RESULT!")
      "" + e.toString()
  }

  def deserialize(in: String): Employees = {
    implicit val formats = DefaultFormats
    read[Employees](in)
  }
}
