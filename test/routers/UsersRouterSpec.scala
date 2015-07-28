package routers

import models.Model.Implicits._
import models.Model.User
import play.api.{BuiltInComponentsFromContext, Application, ApplicationLoader}
import play.api.ApplicationLoader.Context
import play.api.libs.json._
import play.api.routing.Router
import play.api.test._
import repository.DefaultUsersRepository

class UsersRouterSpec extends PlaySpecification {

  object FakeUsersRouter extends DefaultUsersRepository with UsersRouter {
    def apply(): Router.Routes = routes
  }

  val fakeRouter = Router.from(FakeUsersRouter())
  
  val fakeAppLoader = new ApplicationLoader() {
    def load(context: Context): Application = new BuiltInComponentsFromContext(context) {
      def router = fakeRouter
    }.application
  }

  "Users Router" should {

    "Not find the user" in new WithApplicationLoader(fakeAppLoader) {
      val fakeRequest = FakeRequest(GET, "/users/123")
      val Some(result) = route(fakeRequest)

      status(result) must equalTo(NOT_FOUND)
    }

    "Create and Find the user" in new WithApplicationLoader(fakeAppLoader) {
      val body = Json.toJson(User(87, "Gabriel"))
      val fakePost = FakeRequest(POST, "/users").withJsonBody(body)
      val Some(postResult) = route(fakePost)

      status(postResult) must equalTo(CREATED)

      val fakeRequest = FakeRequest(GET, "/users/87")
      val Some(result) = route(fakeRequest)

      status(result) must equalTo(OK)
    }

    "Have a specific handler to GET an User by id" in new WithApplication() {
      val fakeRequest = FakeRequest(GET, "/users/87")
      val handler = fakeRouter.handlerFor(fakeRequest)

      handler must be_!=(None)
    }

    "Have a specific handler to CREATE an User" in new WithApplication() {
      val body = User(87, "Gabriel")
      val fakeRequest = FakeRequest(POST, "/users").withBody(body)
      val handler = fakeRouter.handlerFor(fakeRequest)

      handler must be_!=(None)
    }

    "Have no handler" in new WithApplication() {
      val fakeRequest = FakeRequest(GET, "")
      val handler = fakeRouter.handlerFor(fakeRequest)

      handler must be_==(None)
    }

  }

}
