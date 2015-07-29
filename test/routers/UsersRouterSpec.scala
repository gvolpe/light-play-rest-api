package routers

import models.Model.Implicits._
import models.Model.User
import play.api.libs.json._
import play.api.routing.Router
import play.api.test._

class UsersRouterSpec extends BaseRouterSpecification {

  val fakeRouter = Router.from(FakeUsersRouter())

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
      contentType(result) must beSome("application/json")
      contentAsJson(result) must equalTo(body)
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
