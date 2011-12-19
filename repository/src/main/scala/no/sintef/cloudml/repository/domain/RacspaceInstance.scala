package no.sintef.cloudml.repository.domain

case class RackspaceInstance(size: String = "Medium", flavor: Int = 1) extends Instance()
