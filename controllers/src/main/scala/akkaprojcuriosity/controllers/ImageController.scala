package akkaprojcuriosity.controllers

import akka.http.scaladsl.server.Directives.{complete, get, path}
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._

class ImageController {

  val route =
      path("hello") {
        get {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
        }
      }
}
//https://api.nasa.gov/planetary/apod?api_key=bK120deFy1dvuYLzdlEJON9qFww3jSegOYTLoTeP



/*





import java.nio.charset.Charset

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.util.{Failure, Success}
//import akka.AkkaException
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.stream.ActorMaterializer
import akka.util.ByteString
import spray.json._
//import akka.http.scaladsl.model._

import akkahr.PhotoManifest
import akkahr.ManifestJsonSupport
import akkahr.Manifest

//https://api.nasa.gov/planetary/apod?api_key=bK120deFy1dvuYLzdlEJON9qFww3jSegOYTLoTeP

object Client extends ManifestJsonSupport {

  val nasaApiUrl = "https://api.nasa.gov/mars-photos/api/v1"
  val key = "bK120deFy1dvuYLzdlEJON9qFww3jSegOYTLoTeP"
  val rover = "curiosity"

  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  def main(args: Array[String]): Unit = {

    getManifest().onComplete {
//      case Success(url: String) =>
//        println(url)
      case Success(m: Manifest) => println(m.photo_manifest.photos.maxBy((_.sol)))
      case Failure(e) =>
        println("Failure: " + e)
    }

  }

//  def getManifest(): Future[String] = {
    def getManifest(): Future[Manifest] = {
    val responseFuture: Future[HttpResponse] =
      Http().singleRequest(HttpRequest(uri = s"$nasaApiUrl/manifests/$rover?api_key=$key"))

    responseFuture.flatMap {
      res: HttpResponse =>
        res.entity.dataBytes.runFold(ByteString(""))(_ ++ _).map { body =>
//          body.decodeString(Charset.defaultCharset()).parseJson.prettyPrint
          body.decodeString(Charset.defaultCharset()).parseJson.convertTo[Manifest]
        }
    }
  }
}







 */