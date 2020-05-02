package akkaprojcuriosity.server

import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives.complete
import akka.http.scaladsl.server._
import akkaprojcuriosity.datasource.{RedisDataSource, RequestInfoEntry, Utils}

class Handlers(requestInfoEntry: RequestInfoEntry) {

  def rejectionHandler: RejectionHandler =
    RejectionHandler.newBuilder()
      .handle {
        case MissingCookieRejection(cookieName) =>
          requestInfoEntry.incrementRjectedNum
          complete(HttpResponse(BadRequest, entity = "No cookies, no service!!!"))
      }
      .handle {
        case AuthorizationFailedRejection =>
          requestInfoEntry.incrementRjectedNum
          complete((Forbidden, "You're out of your depth!"))
      }
      .handle {
        case ValidationRejection(msg, _) =>
          requestInfoEntry.incrementRjectedNum
          complete((InternalServerError, "That wasn't valid! " + msg))
      }
      .handleAll[MethodRejection] { methodRejections =>
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
