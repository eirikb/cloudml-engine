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
package no.sintef.cloudml.repository.domain

import scala.actors.Actor
import scala.actors.Actor._
import scala.collection.mutable.HashMap
import scala.collection.mutable.MutableList

import no.sintef.cloudml.kernel.domain.Node

case class AddProperty(name: String, value: String)
case class SetStatus(status: Status.Value)

object Status extends Enumeration {
    val Configuring, Building, Starting, Started = Value
}

object Event extends Enumeration {
    val Property, Status = Value
}

case class RuntimeInstance(node: Node) extends Actor {
    private type Listener = (Event.Value) => Unit

    private var listeners: List[Listener] = Nil

    val properties = new HashMap[String, String]
    var status = Status.Configuring

    def addListener(listener: Listener) {
        listeners = listener +: listeners
    }

    def act() {
        loop {
            receive {
                case AddProperty (name, value) => 
                    properties(name) = value
                    listeners.foreach(_(Event.Property))
                case SetStatus (s) =>
                    status = s
                    listeners.foreach(_(Event.Status))
            }
        }
    }
}
