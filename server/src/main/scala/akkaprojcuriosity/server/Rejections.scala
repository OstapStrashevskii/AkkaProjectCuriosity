package akkaprojcuriosity.server

import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives.complete
import akka.http.scaladsl.server._
import akkaprojcuriosity.datasource.{RedisDataSource, RequestInfoEntry, Utils}

class Rejections(requestInfoEntry: RequestInfoEntry) {

  implicit def rejectionHandler: RejectionHandler =
    RejectionHandler.newBuilder()
      .handle {
        case MissingCookieRejection(cookieName) =>
          println("fff")
          requestInfoEntry.incrementRjectedNum
          complete(HttpResponse(BadRequest, entity = "No cookies, no service!!!"))
      }
      .handle {
        case AuthorizationFailedRejection =>
          println("fff2")
          requestInfoEntry.incrementRjectedNum
          complete((Forbidden, "You're out of your depth!"))
      }
      .handle {
        case ValidationRejection(msg, _) =>
          println("fff3")
          requestInfoEntry.incrementRjectedNum
          complete((InternalServerError, "That wasn't valid! " + msg))
      }
      .handleAll[MethodRejection] { methodRejections =>
        println("fff4")
        requestInfoEntry.incrementRjectedNum//todo sideeffect ??
        val names = methodRejections.map(_.supported.name)
        complete((MethodNotAllowed, s"Can't do that! Supported: ${names mkString " or "}!"))
      }
      .handleNotFound {
        println("fff4")
        requestInfoEntry.incrementRjectedNum
        complete((NotFound, "Not herere bldghad!"))
      }
      .result()

}
