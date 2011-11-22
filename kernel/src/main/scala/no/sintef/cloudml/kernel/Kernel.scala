package no.sintef.cloudml.kernel

import net.liftweb.json._
import net.liftweb.json.JsonAST._
import net.liftweb.json.Serialization.read

import no.sintef.cloudml.engine._
import no.sintef.cloudml.engine.infrastructure.InfrastructureEngine

object Kernel {

    def create(templateJson: String) {
        val template = deserialize(templateJson)
        InfrastructureEngine(template)
    }

    def deserialize(in: String): Template = {
        implicit val formats = DefaultFormats
        read[Template](in)
    }
}
