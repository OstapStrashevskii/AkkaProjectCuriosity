package akkaprojcuriosity.services

import akkaprojcuriosity.client.Client

import scala.concurrent.{ExecutionContext, Future}
import akkaprojcuriosity.dto.Photo

class ImageService(apiClient: Client)(implicit executionContext: ExecutionContext) {

  def getRandomPhoto: Future[Photo] = {
    apiClient.futurePhotos.map {
      futPhotos =>
        val r = new scala.util.Random
        futPhotos.photos(r.nextInt(100))
    }
  }
}
