light-play-rest-api
===================

[ ![Codeship Status for gvolpe/light-play-rest-api](https://codeship.com/projects/d01bfc40-1767-0133-8ea3-06c83ac03245/status?branch=master)](https://codeship.com/projects/93580)

This project aims to be the reference to create a Light Weight REST API using [Play Framework 2.4.x](https://www.playframework.com/).

## About

We are using customized routers, easy to re-use them and fully testables. Every router represents a funcionality (separation of concerns). No more single 'Routes' file that becomes huge with the time.

## How it works?

The entry point is a custom class that extends [ApplicationLoader](https://www.playframework.com/documentation/tr/2.4.x/api/scala/index.html#play.api.ApplicationLoader) which defines the 'load' method.

```scala
class Boot extends ApplicationLoader {

  def load(context: Context): Application = ???

}
```
To indicate that we want to use this custom loader, we have to specify it in the **application.conf** file.

```
play.application.loader = Boot
```

After that we have to create the customized routers. This project has 3 sample routers for Users, Products and Orders. This is how it looks:

```scala
object UsersRouter extends DefaultUsersRepository with UsersRouter {
  def apply(): Router.Routes = routes
}

trait UsersRouter {

  self: UsersRepository =>

  def routes: Router.Routes = {

    case GET(p"/users/${long(id)}") => Action.async {
      find(id) map {
        case Some(user) => Ok(Json.toJson(user))
        case None => NotFound
      }
    }

    case POST(p"/users") => Action.async(parse.json[User]) { implicit request =>
      val user = request.body
      save(user) map (_ => Created)
    }

  }

}
```

Since the type [Router.Routes](https://www.playframework.com/documentation/tr/2.4.x/api/scala/index.html#play.api.routing.Router$@Routes=PartialFunction[play.api.mvc.RequestHeader,play.api.mvc.Handler]) is a **PartialFunction[RequestHeader, Handler]**, we can define the cases in our apply() method.

### Putting the pieces together

After all, we are able to combine our Routes in a defined Router as shown in the code below:

```scala
def router: Router = Router.from {
  UsersRouter() orElse
  ProductsRouter() orElse
  OrdersRouter()
}
```

There are different ways to combine PartialFunctions to get only one. We choose the first one but We can, for instance, define the Router by reducing a List of PartialFunctions:

```scala
def router: Router = Router.from {
  val routers = List(UsersRouter(), ProductsRouter(), OrdersRouter())
  routers reduceLeft (_ orElse _)
}
```

You can find more information about the new Routing system in the [Official Documentation](https://www.playframework.com/documentation/2.4.x/ScalaSirdRouter).

That's enough to start a Light REST API from the scratch ***thinking seriously in clean coding***.

## Testing

We choose **Specs2** to test Play's applications. As it's explained in the documentation, we are able to [test the Routers](https://www.playframework.com/documentation/2.4.x/ScalaFunctionalTestingWithSpecs2#Testing-the-router) instead of call directly to an Action.

So we start creating a Specification for the UserRouterSpec as shown below:

```scala
class UsersRouterSpec extends PlaySpecification {
   ......
}
```

Then we create a FakeUsersRouter to replace the Real Users Repository for an InMemoryRepository (Default).

```scala
object FakeUsersRouter extends DefaultUsersRepository with UsersRouter {
  def apply(): Router.Routes = routes
}
```

Finally we create a fakeApplicationLoader to start testing the Routers.

```scala
val fakeRouter = Router.from(FakeUsersRouter())
  
val fakeAppLoader = new ApplicationLoader() {
  def load(context: Context): Application = new BuiltInComponentsFromContext(context) {
    def router = fakeRouter
  }.application
}
```

Now we are able to write our tests, using our Fake Application Loader.

```scala
"Users Router" should {

  "Not find the user" in new WithApplicationLoader(fakeAppLoader) {
    val fakeRequest = FakeRequest(GET, "/users/123")
    val Some(result) = route(fakeRequest)

    status(result) must equalTo(NOT_FOUND)
  }

  ... More cases ...
}
```

To learn more see the [completed test specification](https://github.com/gvolpe/light-play-rest-api/blob/master/test/routers/UsersRouterSpec.scala).

## License

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this project except in compliance with
the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0.

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific
language governing permissions and limitations under the License.
