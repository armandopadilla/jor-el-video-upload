import java.io.File

import akka.actor.{Actor, ActorLogging, Props}
import akka.http.scaladsl.server.directives.FileInfo

import akka.http.scaladsl.server.Directives._

object VideoUploadRegistryActor {
  final case class ActionPerformed(description: String)
  final case class UploadVideo()

  def props: Props = Props[VideoUploadRegistryActor]
}


class VideoUploadRegistryActor extends Actor with ActorLogging {
  import VideoUploadRegistryActor._

  def receive: Receive = {
    case UploadVideo() =>
      sender() ! ActionPerformed(s"Video upoaded successfully.")
  }

}
