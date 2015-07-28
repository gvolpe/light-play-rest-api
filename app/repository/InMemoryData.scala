package repository

import models.Model.User

import scala.collection.mutable

object InMemoryData {

  val users = mutable.HashMap[Long, User]()

}
