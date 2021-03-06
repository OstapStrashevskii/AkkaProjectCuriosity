package akkaprojcuriosity.controllers

import akka.http.scaladsl.server.Directives.{complete, get, path}
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akkaprojcuriosity.services.RequestInfoService

class RequestInfoController(requestInfoService: RequestInfoService) {

  val route =
      path("info") {
        get {
          val rejected = requestInfoService.getRejectedNum.getOrElse("smth wrong with DB")
          val responseHtml =
                  s"""
                    |<h3>REJECTED: $rejected</h3>
                    |
                    |""".stripMargin
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, responseHtml))
        }
      }
}
