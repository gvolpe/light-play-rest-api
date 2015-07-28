package routers

import play.api.libs.concurrent.Execution.Implicits._
import play.api.mvc.Action
import play.api.mvc.Results._
import play.api.routing.Router
import play.api.routing.sird._
import scala.concurrent.Future

object ProductsRouter {

  def apply() : Router.Routes = {

    case GET(p"/products/$id") => Action.async {
      Future(Ok(s"Product with id: $id"))
    }

  }

}
