package no.sintef.cloudml.engine

import no.sintef.cloudml.kernel.domain._
import no.sintef.cloudml.repository._
import no.sintef.cloudml.engine.infrastructure.InfrastructureEngine

object Engine {

    def apply(account: Account, template: Template) {
       var instances = for (i <- template.nodes) yield Instance("default", "dev")
       instances = Repository.magic(instances)
       InfrastructureEngine(account, instances)
    }
}
