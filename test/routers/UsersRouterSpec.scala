package routers

import models.Model.User
import play.api.routing.Router
import play.api.test._

class UsersRouterSpec extends PlaySpecification {

  "Users Router" should {

    val users = UsersRouter()

    "Have a specific handler to GET a User by id" in new WithApplication() {
      val router = Router.from(users)

      val fakeRequest = FakeRequest(Helpers.GET, "/users/87")
      val handler = router.handlerFor(fakeRequest)

      handler must be_!=(None)
    }

    "Have a specific handler to CREATE a User" in new WithApplication() {
      val router = Router.from(users)

      val body = User(87, "Gabriel")
      val fakeRequest = FakeRequest(Helpers.POST, "/users/87").withBody(body)
      val handler = router.handlerFor(fakeRequest)

      handler must be_!=(None)
    }

    "Have no handler" in new WithApplication() {
      val router = Router.from(users)

      val fakeRequest = FakeRequest(Helpers.GET, "")
      val handler = router.handlerFor(fakeRequest)

      handler must be_==(None)
    }

  }

}
