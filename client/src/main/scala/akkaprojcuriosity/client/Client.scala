package akkaprojcuriosity.client

import java.nio.charset.Charset

import scala.concurrent.Future
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ HttpRequest, HttpResponse}
import akka.stream.{ Materializer}
import akka.util.ByteString
import akkaprojcuriosity.dto._
import play.api.libs.json.{JsError, JsResult, JsSuccess, JsValue, Json }

class Client() {

  val nasaApiUrl = "https://api.nasa.gov/mars-photos/api/v1"
  val key = "bK120deFy1dvuYLzdlEJON9qFww3jSegOYTLoTeP"
  val rover = "curiosity"
  val endPoint = s"$nasaApiUrl/rovers/$rover/photos?sol=100&api_key=$key"

  implicit val system = ActorSystem()
  implicit val materializer: Materializer = Materializer(system)
  implicit val executionContext = system.dispatcher

  val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = endPoint))

  lazy val futurePhotos: Future[Photos] = responseFuture.flatMap {
    res: HttpResponse =>
      println("getting photos from API")
      res.entity.dataBytes.runFold(ByteString(""))(_ ++ _).map { body =>
        val str = body.decodeString(Charset.defaultCharset())
        println(str)
        val js: JsValue = Json.parse(str)
        val parseResult: JsResult[Photos] = Json.fromJson[Photos](js)
        parseResult match {
          case JsSuccess(photos, _) =>
            println("photos from API got successfully")
            photos
          case e @ JsError(_) =>
            println("Errors: " + JsError.toJson(e).toString())
            Photos(Seq.empty[Photo])
        }
      }
  }
}
