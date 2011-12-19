package no.sintef.cloudml.repository.domain

case class RuntimeInstance(ip: String,
  id: String,
  providerId: String,
  location: String,
  name: String,
  group: String,
  hardware: String,
  imageId: String,
  operatingSystem: String,
  state: String,
  privateAddresses: String,
  publicAddresses: String,
  credentials: String
) extends Instance()

