package no.sintef.cloudml.engine

import no.sintef.cloudml.kernel.Kernel
import no.sintef.cloudml.kernel.domain._
import no.sintef.cloudml.repository._
import no.sintef.cloudml.engine.infrastructure.InfrastructureEngine

object Engine {

    def apply(accountJson: String, templatesJson: List[String]) {
        val account  = Kernel.deserializeAccount(accountJson)
        val templates = templatesJson.map(templateJson =>
            Kernel.deserializeTemplate(templateJson)
        ) 

        val instances = Repository.mapping(account, templates)

        InfrastructureEngine.apply(account, instances)
    }
}
