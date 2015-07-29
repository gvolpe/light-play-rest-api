package routers

import play.api.routing.Router
import play.api.test._

class OrdersRouterSpec extends BaseRouterSpecification {

  // In the near future it will be changed by a fake router
  val fakeRouter = Router.from(OrdersRouter())

  "Orders Router" should {

    "Find the order for the user" in new WithApplicationLoader(fakeAppLoader) {
      val fakeRequest = FakeRequest(GET, "/users/123/orders")
      val Some(result) = route(fakeRequest)

      status(result) must equalTo(OK)
    }

    "Have a specific handler to GET a Product by id" in new WithApplication() {
      val fakeRequest = FakeRequest(GET, "/users/123/orders")
      val handler = fakeRouter.handlerFor(fakeRequest)

      handler must be_!=(None)
    }

  }

}
