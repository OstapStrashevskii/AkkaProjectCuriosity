package akkaprojcuriosity.controllers

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives.{complete, get, path, _}
import akkaprojcuriosity.services.ImageService

import scala.util.{Failure, Success}

class ImageController(imageService: ImageService) {

  val route =
    path("image") {
      get {
        onComplete(imageService.getRandomPhoto) {
          case Success(res) =>
            val img = res.img_src
            val responseHtml =
              s"""
                 |<h1 style="color:#ccddee; opacity: 0.5; text-indent: 15px;">Mars is fancy planet:</h1>
                 |<img style="margin-top:-60px; width: 100%; float: left;" src="$img"/>
                 |
                 |""".stripMargin
            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, responseHtml))
          case Failure(er) => complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Sorry, I can't give you image cause of:</h1>" + er))
        }
      }
    }
}
