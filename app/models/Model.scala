package models

import play.api.libs.json.Json

object Model {

  case class User(id: Long, name: String)
  case class Product(id: Long, name: String, price: Double)

  object Implicits {

    implicit val userFormat = Json.format[User]
    implicit val productFormat = Json.format[Product]

  }

}
