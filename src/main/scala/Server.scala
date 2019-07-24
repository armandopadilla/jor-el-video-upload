import akka.http.scaladsl.server.Route
import akka.http.scaladsl.Http

import akka.actor.{ActorRef, ActorSystem}
import akka.stream.ActorMaterializer

import scala.concurrent.{ ExecutionContext, Await, Future }
import scala.concurrent.duration.Duration
import scala.util.{ Failure, Success }



object Server extends App with VideoUploadRoutes {

  implicit val system: ActorSystem = ActorSystem("videouploadServer")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContext = system.dispatcher

  val videoUploadRegistryActor: ActorRef = system.actorOf(VideoUploadRegistryActor.props, "videoUploadRegistryActor")

  lazy val routes: Route = videoUploadRoutes

  val serverBinding: Future[Http.ServerBinding] = Http().bindAndHandle(routes, "localhost", 8080)

  serverBinding.onComplete {
    case Success(bound) => println(s"Video Upload API online http://${bound.localAddress.getHostString}:${bound.localAddress.getPort}/")
    case Failure(e) =>
      Console.err.println(s"Server could not start.")
      e.printStackTrace()
      system.terminate()
  }

  Await.result(system.whenTerminated, Duration.Inf)
}