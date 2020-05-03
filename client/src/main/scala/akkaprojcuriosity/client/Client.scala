package akkaprojcuriosity.client


import java.nio.charset.Charset

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.util.{Failure, Success}
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpRequest, HttpResponse}
import akka.stream.ActorMaterializer
import akka.util.ByteString
import akkaprojcuriosity.dto._
import play.api.libs.json.JsResult.Exception
import play.api.libs.json.{JsError, JsResult, JsSuccess, JsValue, Json, OFormat}

import scala.util.parsing.json
import scala.util.parsing.json.JSONObject
//import play.api.libs.json.
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport._

//https://api.nasa.gov/planetary/apod?api_key=bK120deFy1dvuYLzdlEJON9qFww3jSegOYTLoTeP

object Client extends App {

  val nasaApiUrl = "https://api.nasa.gov/mars-photos/api/v1"
  val key = "bK120deFy1dvuYLzdlEJON9qFww3jSegOYTLoTeP"
  val rover = "curiosity"
  val endPoint = s"$nasaApiUrl/rovers/$rover/photos?sol=100&api_key=$key"




  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.dispatcher

  val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = endPoint))


  import akka.http.scaladsl.unmarshalling.Unmarshal
//  import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
//  import spray.json.DefaultJsonProtocol._

//  case class Pet(name: String)
//  implicit val petFormat = jsonFormat1(Pet)
//  val pet: Future[Pet] = Unmarshal(res).to[Pet]


//  object Photos {
//    implicit lazy val photosFormat: OFormat[Photos] = Json.format[Photos]
//  }

  private val futurePhotos: Future[Photos] = responseFuture.flatMap {
    res: HttpResponse =>
      res.entity.dataBytes.runFold(ByteString(""))(_ ++ _).map { body =>
        val str = body.decodeString(Charset.defaultCharset())
        println(str)
        val js: JsValue = Json.parse(str)
        val parseResult: JsResult[Photos] = Json.fromJson[Photos](js)
//        Json.prettyPrint(parseResult)
        parseResult match {
          case JsSuccess(photos, _) => photos
          case e @ JsError(_) =>
            println("Errors: " + JsError.toJson(e).toString())
            Photos(Seq.empty[Photo])
        }
      }
  }




  futurePhotos.onComplete {
    case Success(res) => println(res)
    case Failure(er) => println(er)
  }


//  responseFuture
//    .onComplete {
//      case Success(res) => println(res)
//
//
//
////        res.get
//        println(res.entity)
////        Json.stringify(res.entity)
//        val photos: Future[Seq[Photo]] = Unmarshal(res).to[Seq[Photo]]
//        println(photos)
//      case Failure(_)   => sys.error("something wrong")
//    }











//  val nasaApiUrl = "https://api.nasa.gov/mars-photos/api/v1"
//  val key = "bK120deFy1dvuYLzdlEJON9qFww3jSegOYTLoTeP"
//  val rover = "curiosity"

//  implicit val system: ActorSystem = ActorSystem()
//  implicit val materializer: ActorMaterializer = ActorMaterializer()
//  implicit val executionContext: ExecutionContextExecutor = system.dispatcher
//
//  def main(args: Array[String]): Unit = {
//
//    getManifest().onComplete {
////      case Success(url: String) =>
////        println(url)
//      case Success(m: Manifest) => println(m.photo_manifest.photos.maxBy((_.sol)))
//      case Failure(e) =>
//        println("Failure: " + e)
//    }
//
//  }
//
////  def getManifest(): Future[String] = {
//    def getManifest(): Future[Manifest] = {
//    val responseFuture: Future[HttpResponse] =
//      Http().singleRequest(HttpRequest(uri = s"$nasaApiUrl/manifests/$rover?api_key=$key"))
//
//    responseFuture.flatMap {
//      res: HttpResponse =>
//        res.entity.dataBytes.runFold(ByteString(""))(_ ++ _).map { body =>
////          body.decodeString(Charset.defaultCharset()).parseJson.prettyPrint
//          body.decodeString(Charset.defaultCharset()).parseJson.convertTo[Manifest]
//        }
//    }
//  }
}
