package akkaprojcuriosity.dto

import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json.{ Format, Json, OFormat }

//todo not typed yet

case class Photos (photos: Seq[Photo])

object Photos {
  implicit lazy val photosFormat: OFormat[Photos] = Json.format[Photos]
}

//case class Photo (id: Int, sol: String, camera: Camera, img_src: String, earth_date: String, rover: Rover)//todo if one of fields is abscent JSError occured while parsing
case class Photo (id: Int, camera: Camera, img_src: String, earth_date: String, rover: Rover)

object Photo {
  implicit lazy val photoFormat: OFormat[Photo] = Json.format[Photo]
}

case class Camera (id: Int, name: String, rover_id: Int, full_name: String)

object Camera {
  implicit lazy val cameraFormat: OFormat[Camera] = Json.format[Camera]
}

//case class Rover(id: Int, name: String, loading_date: String, launch_date: String, status: String, max_sol: Int, max_date: String, total_photos: Int, cameras: Seq[Camera])
case class Rover(id: Int, name: String, launch_date: String, status: String, max_sol: Int, max_date: String, total_photos: Int)

object Rover {
  implicit lazy val cameraFormat: OFormat[Rover] = Json.format[Rover]
}


