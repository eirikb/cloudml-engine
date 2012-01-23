CloudML
-

Templating language and engine for multicloud deployments.  
Currently supports instance initialization.

Build
--

    mvn clean install

Usage
--

The library is still under development, but this should be sufficient:
(Hosted by CloudBees - https://repository-eirikb.forge.cloudbees.com/release/)  

* Add this to your pom

        <repositories>
            ...
            <repository>
                <id>cloudml-engine</id>
                <url>https://repository-eirikb.forge.cloudbees.com/release/</url>
            </repository>
        </repositories>

        <dependencies>
            ...
            <dependency>
                <groupId>no.sintef</groupId>
                <artifactId>engine</artifactId>
                <version>0.1</version>
            </dependency>
        </dependencies>

* Create an account in JSON format

        val account = """{
            "provider": "aws-ec2", 
            "identity": "...", 
            "credential": "..."
            }"""

* Create a template in JSON format

        val template = """{"nodes": [{
                "name": "testnode", "minRam": 0, "minCores": 0
            }]}"""

* This code should be enough

        import no.sintef.cloudml.engine.Engine
        ...
        val runtimeInstances = Engine(account, List(template))

