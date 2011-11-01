package no.sintef.cloudml;

import no.sintef.cloudml.cloudconnector.JcloudsConnector;

public class CloudConnectorFactory {

	private CloudConnectorFactory() {
	}

	public static CloudConnectorFactory getInstance() {
		return CloudConnectorFactoryHolder.INSTANCE;
	}

	public static CloudConnector getCloudConnector() {
		return new JcloudsConnector();
	}

	private static class CloudConnectorFactoryHolder {

		private static final CloudConnectorFactory INSTANCE = new CloudConnectorFactory();
	}
}
