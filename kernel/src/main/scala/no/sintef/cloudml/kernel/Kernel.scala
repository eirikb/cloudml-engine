package no.sintef.cloudml.kernel

import net.liftweb.json._
import net.liftweb.json.JsonAST._
import net.liftweb.json.Serialization.read

import no.sintef.cloudml.kernel.domain._

object Kernel {

    def deserializeTemplate(in: String): Template = {
        implicit val formats = DefaultFormats
        read[Template](in)
    }

    def deserializeAccount(in: String): Account = {
        implicit val formats = DefaultFormats
        read[Account](in)
    }
}
