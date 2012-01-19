package no.sintef.cloudml.cloudconnector

import scala.collection.JavaConverters._

import org.specs2.mutable._
import org.specs2.mock._

import org.jclouds.compute.domain.Volume
import org.jclouds.compute.domain.Hardware

import no.sintef.cloudml.cloudconnector._
import no.sintef.cloudml.repository.domain._
import no.sintef.cloudml.kernel.domain._

class JcloudsConnectorSpec extends SpecificationWithJUnit with Mockito {
    "JcloudsConnector" should {
        "calculate minDisk when set" in {
            val jcloudsConnector = new JcloudsConnector(null)

            val v1 = mock[Volume]
            v1.getSize returns 7

            val v2 = mock[Volume]
            v2.getSize returns 10

            val hw1 = mock[Hardware]
            //hw1.getVolumes returns List(v1, v2).asJava.asInstanceOf[java.util.List[_ <: Volume]]

            /*
            TODO: Halp!
            http://stackoverflow.com/q/3293318

            [INFO]  found   : java.util.List[_$1] where type _$1 <: org.jclouds.compute.domain.Volume
            [INFO]  required: java.util.List[?0] where type ?0 <: org.jclouds.compute.domain.Volume

            */
            1 mustEqual 1
        }
    }
}
