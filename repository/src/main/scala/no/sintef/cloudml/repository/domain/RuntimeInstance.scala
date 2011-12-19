package no.sintef.cloudml.repository.domain

case class RuntimeInstance(id: String,
  privateAddress: String,
  publicAddress: String
) extends Instance()
