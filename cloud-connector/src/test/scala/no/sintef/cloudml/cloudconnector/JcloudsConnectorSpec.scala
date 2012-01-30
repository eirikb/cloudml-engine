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
            val v2 = mock[Volume]
            val v3 = mock[Volume]
            val v4 = mock[Volume]

            v1.getSize returns 2
            v2.getSize returns 3
            v3.getSize returns 6
            v4.getSize returns 8

            val hardware1 = mock[Hardware]
            val hardware2 = mock[Hardware]

            hardware1.getVolumes.asInstanceOf[java.util.List[Object]] returns 
                    java.util.Arrays.asList(v1, v2)

            hardware2.getVolumes.asInstanceOf[java.util.List[Object]] returns 
                    java.util.Arrays.asList(v3, v4)

            jcloudsConnector.findHardwareByDisk(List(hardware1, hardware2), 0) mustEqual hardware1
            jcloudsConnector.findHardwareByDisk(List(hardware1, hardware2), 4) mustEqual hardware1
            jcloudsConnector.findHardwareByDisk(List(hardware1, hardware2), 11) mustEqual hardware2
            jcloudsConnector.findHardwareByDisk(List(hardware1, hardware2), 100) must throwA[RuntimeException]
        }
    }
}
