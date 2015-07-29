package routers

import play.api.routing.Router
import play.api.test._

class ProductsRouterSpec extends BaseRouterSpecification {

  // In the near future it will be changed by a fake router
  val fakeRouter = Router.from(ProductsRouter())

  "Products Router" should {

    "Find the product" in new WithApplicationLoader(fakeAppLoader) {
      val fakeRequest = FakeRequest(GET, "/products/123")
      val Some(result) = route(fakeRequest)

      status(result) must equalTo(OK)
    }

    "Have a specific handler to GET a Product by id" in new WithApplication() {
      val fakeRequest = FakeRequest(GET, "/products/87")
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
