package no.sintef.cloudml.cloudconnector


class JcloudsConnector {

  def createInstances(instances: Any) {

    var group = "default";
    var keyPair = "dev";
    val keys = null;

      println("Oh well")

    //    ComputeServiceContext context = null;
    //
    //    try {
    //      context = new ComputeServiceContextFactory().createContext("aws-ec2",
    //        keys.getAccesskeyid(), keys.getSecretkey());
    //
    //      Template template = context.getComputeService().templateBuilder().locationId("eu-west-1").build();
    //
    //      template.getOptions().as(AWSEC2TemplateOptions.class).securityGroups(group);
    //
    //    template.getOptions().as(AWSEC2TemplateOptions.class).keyPair(keyPair);
    //
    //    Set <? extends NodeMetadata > nodes = context.getComputeService().createNodesInGroup("webserver", 2, template);
    //
    //      final NodeMetadata node = Iterables.get(nodes, 0);
    //    Instance instance = new Instance();
    //    instance.setImageId(node.getImageId());
    //    return Arrays.asList(instance);
    //    } catch (RunNodesException ex)
    //    {
    //      Logger.getLogger(JcloudsConnector.class.getName()).log(Level.SEVERE, "Node could not be started", ex);
    //    } finally
    //    {
    //      if (context != null) {
    //        context.close();
    //      }
    //    }
    //    return null;
  }

  /*
  @Override
  public List<Instance> createInstances(List<Instance> instances) {

  }
  */
}
