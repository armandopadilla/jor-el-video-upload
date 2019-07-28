import java.io.File

import akka.actor.{ActorRef, ActorSystem}
import akka.event.Logging
import akka.http.scaladsl.server.Route
import akka.util.Timeout

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.directives.FileInfo
import akka.http.scaladsl.server.directives.PathDirectives.path
import akka.http.scaladsl.server.directives.MethodDirectives.post
import akka.http.scaladsl.server.directives.MethodDirectives.get
import akka.http.scaladsl.server.directives.RouteDirectives.complete

import scala.concurrent.duration._

import awscala._
import s3._

trait VideoUploadRoutes  {
  lazy val log = Logging(system, classOf[VideoUploadRoutes])

  implicit def system: ActorSystem

  implicit lazy val timeout = Timeout(5.seconds)

  def videoUploadRegistryActor: ActorRef

  lazy val videoUploadRoutes: Route = concat(
    get {
      path("health") {
        complete(200, "{}")
      }
    },
    post{
      path("upload") {
        withRequestTimeout(20.seconds) {
          def tempDestination (fileInfo: FileInfo): File = {
            File.createTempFile(fileInfo.fileName, ".tmp")
          }

          storeUploadedFile("file", tempDestination) {
            case (metadata, file) => {
              val contentType = metadata.contentType.toString()
              val validTypes = Array("video/quicktime", "video/mp4")

              if (!validTypes.contains(contentType)) {
                file.delete()
                complete(400, "Wrong file type.")
              } else {
                val accessKey: String = ""
                val secretKey: String = ""

                implicit val s3 = S3.apply(accessKey, secretKey)(Region.NorthernCalifornia)
                implicit val s3Bucket = s3.bucket("jorel-video-upload")

                s3Bucket.get.putObject(metadata.fileName, file)
                complete(201, "Video uploaded successully.")
              }
            }
          }
        }
      }
    }
  )


}