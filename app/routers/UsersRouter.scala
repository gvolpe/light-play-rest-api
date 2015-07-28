package routers

import models.Model.Implicits._
import models.Model.User
import play.api.mvc.BodyParsers.parse
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json._
import play.api.mvc.Action
import play.api.mvc.Results._
import play.api.routing.Router
import play.api.routing.sird._
import repository.{DefaultUsersRepository, UsersRepository}

object UsersRouter {

  def dao: UsersRepository = new DefaultUsersRepository()

  def apply(): Router.Routes = {

    case GET(p"/users/${long(id)}") => Action.async {
      dao.find(id) map {
        case Some(user) => Ok(Json.toJson(user))
        case None => NotFound
      }
    }

    case POST(p"/users/${long(id)}") => Action.async(parse.json[User]) { implicit request =>
      val user = request.body
      dao.save(user) map (_ => Ok)
    }

  }

}
