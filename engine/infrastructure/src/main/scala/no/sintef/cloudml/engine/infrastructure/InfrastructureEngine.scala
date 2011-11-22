package no.sintef.cloudml.engine.infrastructure

import no.sintef.cloudml.repository._
import no.sintef.cloudml.cloudconnector._

object InfrastructureEngine {

    def apply(account: Account, instances: List[Instance]) {
        val cloudConnector = CloudConnector("jscloud")
        cloudConnector.createInstance(account, instances.head)
    }
}
