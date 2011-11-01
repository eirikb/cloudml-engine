package no.sintef.cloudml;

import no.sintef.cloudml.cloudconnector.JcloudsConnector
import no.sintef.cloudml.connector.CloudConnector

class CloudConnectorFactory private (val instance: CloudConnector) { 
}

object CloudConnectorFactory {
    def create() = {
        new JcloudsConnector()
    }
}