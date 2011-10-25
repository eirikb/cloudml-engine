package no.sintef.cloudml;

import com.google.common.collect.Iterables;
import com.google.gson.Gson;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jclouds.aws.ec2.AWSEC2Client;
import org.jclouds.aws.ec2.compute.AWSEC2TemplateOptions;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.ComputeServiceContextFactory;
import org.jclouds.compute.RunNodesException;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.domain.Template;

public class Engine {

	public static void main(String[] args) throws RunNodesException {
		String group = "default";
		String keyPair = "dev";
		Keys keys = null;

		Gson gson = new Gson();
		try {
			keys = gson.fromJson(new InputStreamReader(new FileInputStream("keys.json")), Keys.class);
		} catch (FileNotFoundException ex) {
			Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
		}

		ComputeServiceContext context = null;

		try {
			context = new ComputeServiceContextFactory().createContext("aws-ec2",
					keys.getAccesskeyid(), keys.getSecretkey());

			Template template = context.getComputeService().templateBuilder().locationId("eu-west-1").build();

			template.getOptions().as(AWSEC2TemplateOptions.class).securityGroups(group);

			template.getOptions().as(AWSEC2TemplateOptions.class).keyPair(keyPair);

			Set<? extends NodeMetadata> nodes = context.getComputeService().createNodesInGroup("webserver", 2, template);

			AWSEC2Client ec2Client = AWSEC2Client.class.cast(context.getProviderSpecificContext().getApi());

			NodeMetadata node = Iterables.get(nodes, 0);
			System.out.println(node);
		} finally {
			if (context != null) {
				context.close();
			}
		}

	}
}
