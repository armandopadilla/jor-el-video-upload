import akka.actor.{ActorRef, ActorSystem}
import akka.event.Logging
import akka.http.scaladsl.model.StatusCode
import akka.http.scaladsl.server.Route
import akka.util.Timeout
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.directives.PathDirectives.path
import akka.http.scaladsl.server.directives.MethodDirectives.post
import akka.http.scaladsl.server.directives.MethodDirectives.get
import akka.http.scaladsl.server.directives.RouteDirectives.complete

import scala.concurrent.duration._

trait VideoUploadRoutes  {
  implicit def system: ActorSystem

  lazy val log = Logging(system, classOf[VideoUploadRoutes])

  def videoUploadRegistryActor: ActorRef

  implicit lazy val timeout = Timeout(5.seconds)

  lazy val videoUploadRoutes: Route = concat(
    get {
      path("health") {
        complete(200, "{}")
      }
    },
    post{
      path("upload") {

        // Check the video format.
        // Send the video to S3.
        complete(201, "Video uploaded successully.")

      }
    }
  )


}