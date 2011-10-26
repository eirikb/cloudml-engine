package no.sintef.cloudml.cloudconnector;

import com.google.common.collect.Iterables;
import com.google.gson.Gson;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.sintef.cloudml.CloudConnector;
import no.sintef.cloudml.domain.Instance;
import no.sintef.cloudml.domain.Keys;
import org.jclouds.aws.ec2.compute.AWSEC2TemplateOptions;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.ComputeServiceContextFactory;
import org.jclouds.compute.RunNodesException;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.domain.Template;

public class JcloudsConnector implements CloudConnector {

	@Override
	public List<Instance> createInstances(List<Instance> instances) {
		String group = "default";
		String keyPair = "dev";
		Keys keys = null;

		Gson gson = new Gson();
		try {
			keys = gson.fromJson(new InputStreamReader(new FileInputStream("keys.json")), Keys.class);
		} catch (FileNotFoundException ex) {
			Logger.getLogger(JcloudsConnector.class.getName()).log(Level.SEVERE, null, ex);
		}

		ComputeServiceContext context = null;

		try {
			context = new ComputeServiceContextFactory().createContext("aws-ec2",
					keys.getAccesskeyid(), keys.getSecretkey());

			Template template = context.getComputeService().templateBuilder().locationId("eu-west-1").build();

			template.getOptions().as(AWSEC2TemplateOptions.class).securityGroups(group);

			template.getOptions().as(AWSEC2TemplateOptions.class).keyPair(keyPair);

			Set<? extends NodeMetadata> nodes = context.getComputeService().createNodesInGroup("webserver", 2, template);

			final NodeMetadata node = Iterables.get(nodes, 0);
			Instance instance = new Instance();
			instance.setImageId(node.getImageId());
			return Arrays.asList(instance);
		} catch (RunNodesException ex) {
			Logger.getLogger(JcloudsConnector.class.getName()).log(Level.SEVERE, "Node could not be started", ex);
		} finally {
			if (context != null) {
				context.close();
			}
		}
		return null;
	}
}
