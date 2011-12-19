package no.sintef.cloudml.repository.domain

case class AWSInstance(size: String = "Medium", flavor: Int = 1) extends Instance()

