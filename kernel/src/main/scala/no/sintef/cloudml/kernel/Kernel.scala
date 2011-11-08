package no.sintef.cloudml.kernel

import net.liftweb.json.JsonParser._

object Kernel {

  def create(templateJson: String) {
    val template = parse(templateJson)
     // .extract[Template]
     println(template)
  }
}
