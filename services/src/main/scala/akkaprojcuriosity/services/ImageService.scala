package akkaprojcuriosity.services

import akkaprojcuriosity.client.Client

import scala.concurrent.Future
//import scala.concurrent.{ ExecutionContext, Future }
import scala.concurrent.ExecutionContext.Implicits.global //todo via class or method
import akkaprojcuriosity.dto.{Photo}

class ImageService(apiClient: Client) {

  def getRandomPhoto: Future[Photo] = {
    apiClient.futurePhotos.map {
      futPhotos =>
        val r = new scala.util.Random
        futPhotos.photos(r.nextInt(100))
    }
  }
}
