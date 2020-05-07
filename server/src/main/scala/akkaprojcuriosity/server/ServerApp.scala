package akkaprojcuriosity.server

import akkaprojcuriosity.controllers.{ImageController, RequestInfoController}
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import scala.concurrent.Future
import akkaprojcuriosity.datasource.{RedisDataSource, RequestInfoEntry, Utils}
import akka.http.scaladsl.server.Directives._
import akkaprojcuriosity.server.ApplicationComponents._

object ServerApp extends App {

//  (
//  implicit val config: Config = ConfigFactory.load(),
//  val executionContext: ExecutionContext,
//  val actorSystem: ActorSystem,
//  val materializer: ActorMaterializer
//  )



  initDB(redisDataSource)

  val bindingFuture: Future[Http.ServerBinding] = ApplicationComponents.http.bindAndHandle(getRoute, "localhost", 8080)

  private def getRoute: Route = {
    handleRejections(ApplicationComponents.handlers.rejectionHandlerWithCounter) {
      new ImageController(ApplicationComponents.imageService).route ~ new RequestInfoController(ApplicationComponents.requestInfoService).route
    }
  }

  private def initDB(dSource: RedisDataSource) = {
    val reqInfoEntry = new RequestInfoEntry(dSource) with Utils
    reqInfoEntry.setRejectedNum(0)
    reqInfoEntry.setSuccessfulNum(0)
  }
}
