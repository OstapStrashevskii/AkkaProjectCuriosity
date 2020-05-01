package akkaprojcuriosity.controllers

import akka.http.scaladsl.server.Directives.{complete, get, path}
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._

class RequestInfoController {

  val route =
//    handleRejections(Rejections.rejectionHandler) {
      path("info") {
        get {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say info to akka-http</h1>"))
        }
      }
//    }
}
