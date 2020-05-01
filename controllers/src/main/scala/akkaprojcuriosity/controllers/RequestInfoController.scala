package akkaprojcuriosity.controllers

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives.{complete, get, path}

class RequestInfoController {
  val route =
    path("info") {
      get {

        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say INFO to akka-http</h1>"))
      }
    }
}