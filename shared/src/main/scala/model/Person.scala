package model

import io.circe.generic.AutoDerivation

case class Person(id: Int, name: String)
object Person extends AutoDerivation
