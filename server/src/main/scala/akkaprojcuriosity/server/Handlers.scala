package akkaprojcuriosity.server

import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives.complete
import akka.http.scaladsl.server._
import akkaprojcuriosity.datasource.{ RequestInfoEntry }

class Handlers(requestInfoEntry: RequestInfoEntry) {

  def rejectionHandlerWithCounter: RejectionHandler = { (rejections: Seq[Rejection]) => //todo while seq I get head only
    if(rejections.size > 0) {
      rejections.head match {
        case MissingCookieRejection(_) =>
          Some(complete(BadRequest, "bad"))
        case AuthenticationFailedRejection(_, _) =>
          Some(complete((Forbidden, "You're out of your depth!")))
        case _ =>
          Some(complete((NotFound, "Not here bldghad!")))
      }
    } else {
      requestInfoEntry.incrementRjectedNum
      Some(complete(BadRequest, "bad request"))
    }
  }
}
