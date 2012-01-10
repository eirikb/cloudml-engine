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

* Build the library
* Add this to your pom

        <groupId>no.sintef.cloudml</groupId>
            <artifactId>engine</artifactId>
            <version>0.1-SNAPSHOT</version>
        </dependency>

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

