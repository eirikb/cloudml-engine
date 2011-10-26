package no.sintef.cloudml;

import java.util.List;
import no.sintef.cloudml.domain.Instance;

public interface CloudConnector {

	public List<Instance> createInstances(List<Instance> instances);
}
