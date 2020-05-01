package akkaprojcuriosity.server

import akka.actor.ActorSystem
import akkaprojcuriosity.controllers.{ImageController, RequestInfoController}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse}
//import akka.http.scaladsl.server.Directives.{complete, get, path}
import akka.http.scaladsl.server.{AuthorizationFailedRejection, MethodRejection, MissingCookieRejection, RejectionHandler, ValidationRejection}
import akka.stream.ActorMaterializer
import akka.http.scaladsl.model.StatusCodes._
import akkaprojcuriosity.controllers
import akkaprojcuriosity.datasource.{RedisDataSource, RequestInfoEntry, Utils}
import akka.http.scaladsl.server.Directives._

object ServerApp extends App {
//  self: ImageControllerImpl =>
  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()

  val redisDataStore: RedisDataSource = new RedisDataSource() with Utils
  val reqInfoEntry = new RequestInfoEntry(redisDataStore) with Utils

  val imageControllerRoute = new ImageController().route
  val requestInfoControllerRoute = new RequestInfoController().route

  val route = imageControllerRoute ~ requestInfoControllerRoute

  def incrementRejection() = {
    reqInfoEntry.incrementRjectedNum
  }

  implicit def rejectionHandler =
    RejectionHandler.newBuilder()
      .handle {
        case MissingCookieRejection(cookieName) =>
          println("fff")
          incrementRejection()
          complete(HttpResponse(BadRequest, entity = "No cookies, no service!!!"))
      }
      .handle {
        case AuthorizationFailedRejection =>
          println("fff2")
          incrementRejection()
          complete((Forbidden, "You're out of your depth!"))
      }
      .handle {
        case ValidationRejection(msg, _) =>
          println("fff3")
          incrementRejection()
          complete((InternalServerError, "That wasn't valid! " + msg))
      }
      .handleAll[MethodRejection] { methodRejections =>
        println("fff4")
        incrementRejection()//todo sideeffect ??
        val names = methodRejections.map(_.supported.name)
        complete((MethodNotAllowed, s"Can't do that! Supported: ${names mkString " or "}!"))
      }
      .handleNotFound { complete((NotFound, "Not here!")) }
      .result()



  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)


//  val route = authController.route ~ securityDirective.authenticated {
//    routeCombiner(userController.route, projectController.route, projectFileController.route)
//  }
//
//  log.info(s"Server online at http://${webServiceConfig.interface}:${webServiceConfig.port}/")
//  Http().bindAndHandle(route, webServiceConfig.interface, webServiceConfig.port)






}
