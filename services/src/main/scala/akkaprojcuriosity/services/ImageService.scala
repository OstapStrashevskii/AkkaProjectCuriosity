package akkaprojcuriosity.services

import akkaprojcuriosity.client.Client
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class ImageService(apiClient: Client) {

  def getRandomImage(): Future[String] = {
    apiClient.futurePhotos.map {
      futPhotos =>
        val r = new scala.util.Random
        futPhotos.photos(r.nextInt(100)).img_src
    }
  }
}
