package routers

import play.api.routing.Router
import play.api.test._

class ProductsRouterSpec extends BaseRouterSpecification {

  // In the near future it will be changed by a fake router
  val fakeRouter = Router.from(ProductsRouter())

  "Products Router" should {

    "Find product by Id" in new WithApplicationLoader(fakeAppLoader) {
      val fakeRequest = FakeRequest(GET, "/products/123")
      val Some(result) = route(fakeRequest)

      status(result) must equalTo(OK)
    }

    "Find all products" in new WithApplicationLoader(fakeAppLoader) {
      val fakeRequest = FakeRequest(GET, "/products")
      val Some(result) = route(fakeRequest)

      status(result) must equalTo(OK)
      contentAsString(result) must contain("order=asc") //Default value
    }

    "Find all products specifying the order" in new WithApplicationLoader(fakeAppLoader) {
      val fakeRequest = FakeRequest(GET, "/products?order=desc")
      val Some(result) = route(fakeRequest)

      status(result) must equalTo(OK)
      contentAsString(result) must contain("order=desc") //Optional parameter
    }

    "Have a specific handler to GET a Product by id" in new WithApplication() {
      val fakeRequest = FakeRequest(GET, "/products/87")
      val handler = fakeRouter.handlerFor(fakeRequest)

      handler must be_!=(None)
    }

    "Have a specific handler to GET All Products" in new WithApplication() {
      val fakeRequest = FakeRequest(GET, "/products")
      val handler = fakeRouter.handlerFor(fakeRequest)

      handler must be_!=(None)
    }

  }

}
