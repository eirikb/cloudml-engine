package no.sintef.cloudml.repository

import no.sintef.cloudml.repository.domain._
import no.sintef.cloudml.kernel.domain._

object Repository {
    def mapping(account: Account, templates: List[Template]): List[Instance] = {
        List.flatten(templates.map(template => 
            template.nodes.map(node =>
                new Instance(node.name, node.minRam)
            )
        ))
    }
}
