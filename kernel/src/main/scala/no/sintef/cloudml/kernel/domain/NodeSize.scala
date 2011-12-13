package no.sintef.cloudml.kernel.domain

sealed trait NodeSize { def name: String }
case object Small extends NodeSize { val name = "Small" } 
case object Medium extends NodeSize { val name = "Medium" } 
case object Large extends NodeSize { val name = "Large" } 
