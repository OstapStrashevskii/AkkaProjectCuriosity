package akkaprojcuriosity.server

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.{ Materializer }
import akkaprojcuriosity.datasource.{RedisDataSource, RequestInfoEntry, Utils }
import akkaprojcuriosity.services.{ImageService, RequestInfoService}
import akkaprojcuriosity.client.Client

object ApplicationComponents {

  implicit val system = ActorSystem("actorSystem")
  implicit val materializer: Materializer = Materializer(system)

  lazy val http = Http()
  lazy val redisDataSource: RedisDataSource = new RedisDataSource() with Utils
  lazy val requestInfoEntry: RequestInfoEntry = new RequestInfoEntry(redisDataSource) with Utils
  lazy val apiClient: Client = new Client()
  lazy val requestInfoService: RequestInfoService = new RequestInfoService(requestInfoEntry)
  lazy val imageService: ImageService = new ImageService(apiClient)
  lazy val handlers: Handlers = new Handlers(requestInfoEntry)
}
