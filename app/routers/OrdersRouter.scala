package routers

import play.api.libs.concurrent.Execution.Implicits._
import play.api.mvc.Action
import play.api.mvc.Results._
import play.api.routing.Router
import play.api.routing.sird._
import scala.concurrent.Future

object OrdersRouter extends OrdersRouter {
  def apply() : Router.Routes = routes
}

trait OrdersRouter {

  def routes : Router.Routes = {

    case GET(p"/users/$id/orders") => Action.async {
      Future(Ok(s"Orders of the user with id: $id"))
    }

  }

}
