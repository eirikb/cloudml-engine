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
package no.sintef.cloudml.cloudconnector

import no.sintef.cloudml.repository.domain._
import no.sintef.cloudml.kernel.domain._

trait CloudConnector {
    def createInstances(template: Template): List[RuntimeInstance]
    def destroyInstance(id: String)
}

object CloudConnector {

    def apply(account: Account, instanceType: String): CloudConnector = instanceType match {
        case "jclouds" =>
            new JcloudsConnector(account);
        case _ =>
            new JcloudsConnector(account);
    }
}
