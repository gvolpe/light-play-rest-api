import play.api.ApplicationLoader.Context
import play.api.routing.Router
import play.api.{Application, ApplicationLoader, BuiltInComponentsFromContext}
import routers.{OrdersRouter, ProductsRouter, UsersRouter}

class Boot extends ApplicationLoader {

  def load(context: Context): Application = new BuiltInComponentsFromContext(context) {

    //val routers = List(UsersRouter(), ProductsRouter(), OrdersRouter())

    def router: Router = Router.from {
      val routers = List(UsersRouter(), ProductsRouter(), OrdersRouter())
      routers reduceLeft(_ orElse _)
      //routers reduceLeft (_ orElse _)
      UsersRouter() orElse
      ProductsRouter() orElse
      OrdersRouter()
    }

  }.application

}
