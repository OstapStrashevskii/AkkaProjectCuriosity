package akkaprojcuriosity.controllers

import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.StatusCodes.{BadRequest, Forbidden, InternalServerError, MethodNotAllowed, NotFound}
import akka.http.scaladsl.server.Directives.complete
import akka.http.scaladsl.server.{AuthorizationFailedRejection, MethodRejection, MissingCookieRejection, RejectionHandler, ValidationRejection}

object Rejections {

  implicit def rejectionHandler =
    RejectionHandler.newBuilder()
      .handle {
        case MissingCookieRejection(cookieName) =>
          println("fff")
          complete(HttpResponse(BadRequest, entity = "No cookies, no service!!!"))
      }
      .handle {
        case AuthorizationFailedRejection =>
          println("fff2")
          complete((Forbidden, "You're out of your depth!"))
      }
      .handle {
        case ValidationRejection(msg, _) =>
          println("fff3")
          complete((InternalServerError, "That wasn't valid! " + msg))
      }
      .handleAll[MethodRejection] { methodRejections =>
        println("fff4")
        val names = methodRejections.map(_.supported.name)
        complete((MethodNotAllowed, s"Can't do that! Supported: ${names mkString " or "}!"))
      }
      .handleNotFound {
        println("fff4")
        complete((NotFound, "Not here!"))
      }
      .result()

}
