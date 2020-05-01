package akkaprojcuriosity.server

import akka.actor.ActorSystem
import akkaprojcuriosity.controllers.ImageControllerImpl
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives.{complete, get, path}
import akka.stream.ActorMaterializer
import akkaprojcuriosity.controllers.ImageController

object ServerApp extends App {
//  self: ImageControllerImpl =>

  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()




//  val route =
//    path("hello") {
//      get {
//        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
//      }
//    }



  val route = new ImageControllerImpl().route


  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)




//  val route = authController.route ~ securityDirective.authenticated {
//    routeCombiner(userController.route, projectController.route, projectFileController.route)
//  }
//
//  log.info(s"Server online at http://${webServiceConfig.interface}:${webServiceConfig.port}/")
//  Http().bindAndHandle(route, webServiceConfig.interface, webServiceConfig.port)



}
