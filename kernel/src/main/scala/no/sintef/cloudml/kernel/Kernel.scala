/**
 * This file is part of CloudML
 *
 * Copyright (C) 2012-  SINTEF ICT
 * Contact: Eirik Brandtzæg <eirikb@eirikb.no>
 *
 * Module: kernel
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
package no.sintef.cloudml.kernel

import net.liftweb.json._
import net.liftweb.json.JsonAST._
import net.liftweb.json.Serialization.read

import no.sintef.cloudml.kernel.domain._

object Kernel {

    def deserializeTemplate(in: String): Template = {
        implicit val formats = DefaultFormats
        read[Template](in)
    }

    def deserializeAccount(in: String): Account = {
        implicit val formats = DefaultFormats
        read[Account](in)
    }
}
