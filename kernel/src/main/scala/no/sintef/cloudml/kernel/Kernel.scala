package no.sintef.cloudml.kernel

import net.liftweb.json._
import net.liftweb.json.JsonAST._
import net.liftweb.json.Serialization.read

import no.sintef.cloudml.engine.Engine
import no.sintef.cloudml.engine.domain._
import no.sintef.cloudml.engine.infrastructure.InfrastructureEngine
import no.sintef.cloudml.cloudconnector._

object Kernel {

    def create(accountJson: String, templateJson: String) {
        val template = deserializeTemplate(templateJson)
        val account = deserializeAccount(accountJson)
        Engine(account, template)
    }

    def deserializeTemplate(in: String): Template = {
        implicit val formats = DefaultFormats
        read[Template](in)
    }

    def deserializeAccount(in: String): Account = {
        implicit val formats = DefaultFormats
        read[Account](in)
    }
}
