package no.sintef.cloudml.repository

import no.sintef.cloudml.repository.domain._
import no.sintef.cloudml.kernel.domain._

object Repository {
    def mapping(account: Account, templates: List[Template]): List[Instance] = {
        List.flatten(templates.map(template => 
            template.nodes.map(node =>
                account.provider match {
                    case "aws-ec2" => new AWSInstance(node.size)
                    case "cloudservers" => new RackspaceInstance(node.size match {
                        case "Small" => 1
                        case "Medium" => 2
                        case "Large" => 3
                        case _ => 2
                      })
                    case _ => new Instance()
                }
            )
        ))
    }
}
