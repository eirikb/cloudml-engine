/**
 * This file is part of CloudML
 *
 * Copyright (C) 2012-  SINTEF ICT
 * Contact: Eirik Brandtzæg <eirikb@eirikb.no>
 *
 * CloudML is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * CloudML is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with CloudML. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package no.sintef.cloudml.engine

import no.sintef.cloudml.kernel.Kernel
import no.sintef.cloudml.kernel.domain._
import no.sintef.cloudml.repository.domain._
import no.sintef.cloudml.repository._
import no.sintef.cloudml.cloudconnector._

object Engine {

    private var cloudConnector: CloudConnector = null

    def apply(accountJson: String, templatesJson: List[String]): List[RuntimeInstance] = {
        val account  = Kernel.deserializeAccount(accountJson)
        val templates = templatesJson.map(templateJson =>
            Kernel.deserializeTemplate(templateJson)
        ) 

        cloudConnector = CloudConnector(account, "jscloud")

        templates.map(template => {
          val instances = Repository.mapping(template)
          cloudConnector.createInstances(template.loadBalancer, instances)
        }).flatten
    }

    def destroyNode(id: String) {
        cloudConnector.destroyInstance(id)
    }
}
