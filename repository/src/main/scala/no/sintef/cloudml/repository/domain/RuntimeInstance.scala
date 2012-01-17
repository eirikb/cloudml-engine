/**
 * This file is part of CloudML
 *
 * Copyright (C) 2012-  SINTEF ICT
 * Contact: Eirik Brandtzæg <eirikb@eirikb.no>
 *
 * Module: repository
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
package no.sintef.cloudml.repository.domain

import scala.actors.Actor
import scala.actors.Actor._
import scala.collection.mutable.HashMap

case class AddProperty(name: String, value: String)

case class RuntimeInstance(instance: Instance) extends Actor {

    val properties = new HashMap[String, String]
    var id = ""

    def act() {
        loop {
            receive {
                case AddProperty (name, value) =>
                    properties(name) = value
            }
        }
    }
}
