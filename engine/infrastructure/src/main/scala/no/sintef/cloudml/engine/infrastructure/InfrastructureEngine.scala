package no.sintef.cloudml.engine.infrastructure

import no.sintef.cloudml.kernel.domain._
import no.sintef.cloudml.cloudconnector._
import no.sintef.cloudml.repository._

object InfrastructureEngine {

    def apply(account: Account, instances: List[Instance]) {
        val cloudConnector = CloudConnector("jscloud")
        cloudConnector.createInstance(account, instances.head)
    }
}
