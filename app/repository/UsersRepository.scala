package repository

import models.Model.User
import play.api.libs.concurrent.Execution.Implicits._
import scala.concurrent.Future

trait UsersRepository {

  def find(id: Long): Future[Option[User]]

  def save(user: User): Future[Unit]

}

class DefaultUsersRepository extends UsersRepository {

  def find(id: Long): Future[Option[User]] = Future {
    InMemoryData.users.get(id)
  }

  def save(user: User): Future[Unit] = Future {
    InMemoryData.users.put(user.id, user)
  }

}
