package no.sintef.cloudml.engine.infrastructure

import no.sintef.cloudml.engine.domain._
import no.sintef.cloudml.repository._

object InfrastructureEngine {

    // Somethings tells me apply should not be used like this...
    def apply(template: Template) {
        val instances = for (node <- template.nodes) yield new Instance("group?", "dev")
    }
}
