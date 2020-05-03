package akkaprojcuriosity.server

import akka.actor.ActorSystem
import akkaprojcuriosity.controllers.{ImageController, RequestInfoController}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse}
import akka.http.scaladsl.server.Route

import scala.concurrent.Future
//import akka.http.scaladsl.server.Directives.{complete, get, path}
import akka.http.scaladsl.server.{AuthorizationFailedRejection, MethodRejection, MissingCookieRejection, RejectionHandler, ValidationRejection}
import akka.stream.ActorMaterializer
import akka.http.scaladsl.model.StatusCodes._
import akkaprojcuriosity.controllers
import akkaprojcuriosity.datasource.{RedisDataSource, RequestInfoEntry, Utils}
import akka.http.scaladsl.server.Directives._
import akkaprojcuriosity.server.ApplicationComponents
import akkaprojcuriosity.server.ApplicationComponents._

object ServerApp extends App {
//  self: ApplicationComponents =>

//  val appComponents = ApplicationComponents
  initDB(redisDataSource)

//  implicit val system = ActorSystem("my-system")
//  implicit val materializer = ActorMaterializer()


  val bindingFuture: Future[Http.ServerBinding] = ApplicationComponents.http.bindAndHandle(getRoute, "localhost", 8080)

  private def getRoute: Route = {
//    handleRejections(appComponents.handlers.forbiddenHandler){
//      handleRejections(appComponents.handlers.badRequestHandler){
//        new ImageController().route ~ new RequestInfoController().route
//      }
//    }
    handleRejections(ApplicationComponents.handlers.rejectionHandlerWithCounter) {
      new ImageController().route ~ new RequestInfoController(ApplicationComponents.requestInfoService).route
    }
  }

  private def initDB(dSource: RedisDataSource) = {
    val reqInfoEntry = new RequestInfoEntry(dSource) with Utils
    reqInfoEntry.setRejectedNum(0)
    reqInfoEntry.setSuccessfullNum(0)
  }
}
