package routers

import play.api.ApplicationLoader.Context
import play.api.routing.Router
import play.api.test.PlaySpecification
import play.api.{Application, ApplicationLoader, BuiltInComponentsFromContext}
import repository.DefaultUsersRepository

abstract class BaseRouterSpecification extends PlaySpecification {

  object FakeUsersRouter extends DefaultUsersRepository with UsersRouter {
    def apply(): Router.Routes = routes
  }

  val fakeAppLoader = new ApplicationLoader() {
    def load(context: Context): Application = new BuiltInComponentsFromContext(context) {
      def router = Router.from {
        List(FakeUsersRouter(), ProductsRouter()) reduceLeft (_ orElse _)
      }
    }.application
  }

}
