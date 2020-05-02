package akkaprojcuriosity.server

import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives.complete
import akka.http.scaladsl.server._
import akkaprojcuriosity.datasource.{RedisDataSource, RequestInfoEntry, Utils}

class Handlers(requestInfoEntry: RequestInfoEntry) {

  def rejectionHandlerWithCounter: RejectionHandler = { (rejections: Seq[Rejection]) => //todo while seq I get head only
    requestInfoEntry.incrementRjectedNum
    rejections.head match {
      case MissingCookieRejection(_) => Some(complete(StatusCodes.BadRequest))
      case AuthenticationFailedRejection(_,_) => Some(complete((Forbidden, "You're out of your depth!")))
      case _ => Some(complete((NotFound, "Not here bldghad!")))
    }
  }

  def badRequestHandler: RejectionHandler = { (rejections: Seq[Rejection]) =>
    println(s"I have encountered rejections: $rejections")
    Some(complete((StatusCodes.BadRequest)))
  }

  def forbiddenHandler: RejectionHandler = { (rejections: Seq[Rejection]) =>
    println(s"I have encountered rejections: $rejections")
    Some(complete((StatusCodes.Forbidden)))
  }

  def rejectionHandler: RejectionHandler =
    RejectionHandler.newBuilder()
      .handle {
        case MissingCookieRejection(cookieName) =>
          complete(HttpResponse(BadRequest, entity = "No cookies, no service!!!"))
      }
      .handle {
        case AuthorizationFailedRejection =>
          complete((Forbidden, "You're out of your depth!"))
      }
      .handle {
        case ValidationRejection(msg, _) =>
          complete((InternalServerError, "That wasn't valid! " + msg))
      }
      .handleAll[MethodRejection] { methodRejections =>
        val names = methodRejections.map(_.supported.name)
        complete((MethodNotAllowed, s"Can't do that! Supported: ${names mkString " or "}!"))
      }
      .handleNotFound {
        complete((NotFound, "Not here bldghad!"))
      }
      .result()



}
