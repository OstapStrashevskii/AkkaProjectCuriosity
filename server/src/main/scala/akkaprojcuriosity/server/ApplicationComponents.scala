package akkaprojcuriosity.server

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akkaprojcuriosity.datasource.{RedisDataSource, RequestInfoEntry, Utils}
import akkaprojcuriosity.services.RequestInfoService

object ApplicationComponents {

  implicit val system = ActorSystem("actorSystem")
  implicit val materializer = ActorMaterializer()

  lazy val http = Http()
  lazy val redisDataSource: RedisDataSource = new RedisDataSource() with Utils
  lazy val requestInfoEntry: RequestInfoEntry = new RequestInfoEntry(redisDataSource) with Utils
  lazy val requestInfoService: RequestInfoService = new RequestInfoService(requestInfoEntry)
  lazy val handlers: Handlers = new Handlers(requestInfoEntry)

}
