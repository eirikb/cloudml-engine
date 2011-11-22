package no.sintef.cloudml.kernel

import net.liftweb.json._
import net.liftweb.json.JsonAST._
import net.liftweb.json.Serialization.read

import no.sintef.cloudml.engine.Engine
import no.sintef.cloudml.engine.domain._
import no.sintef.cloudml.engine.infrastructure.InfrastructureEngine
import no.sintef.cloudml.cloudconnector._

object Kernel {

    def create(templateJson: String) {
        val template = deserialize(templateJson)
        Engine(Account("Test", AuthKeys("Hello, ", "world!")), template)
    }

    def deserialize(in: String): Template = {
        implicit val formats = DefaultFormats
        read[Template](in)
    }
}
