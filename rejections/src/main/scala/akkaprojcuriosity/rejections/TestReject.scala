package akkaprojcuriosity.rejections

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.{complete, get, path}
import akka.http.scaladsl.server.{AuthorizationFailedRejection, MethodRejection, MissingCookieRejection, Rejection, RejectionHandler, Route, ValidationRejection}
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import akkaprojcuriosity.rejections.HandlingRejections.simpleRoute



object TestReject extends App {

  implicit val system = ActorSystem("HandlingRejections")
  implicit val materializer = ActorMaterializer()

  // rejection handler
  val badRequestHandler: RejectionHandler = { (rejections: Seq[Rejection]) =>
    println(s"bad I have encountered rejections: $rejections")
    Some(complete((StatusCodes.BadRequest)))
  }

  val forbiddenHandler: RejectionHandler = { (rejections: Seq[Rejection]) =>
    println(s"forbidden I have encountered rejections: $rejections")
    Some(complete((StatusCodes.Forbidden)))
  }


  val route: Route = path("api") {
    get {
      complete(StatusCodes.OK)
    }
  }

  val handledRoute: Route = handleRejections(forbiddenHandler) {
    route
  }

//  val handledRoute: Route = route


  Http().bindAndHandle(handledRoute, "localhost", 8080)

}
