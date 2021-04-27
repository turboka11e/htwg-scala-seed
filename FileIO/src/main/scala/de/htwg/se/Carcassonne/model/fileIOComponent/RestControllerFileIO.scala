package de.htwg.se.Carcassonne.model.fileIOComponent

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

import scala.concurrent.ExecutionContextExecutor

object RestControllerFileIO {

  def port = 8085

  def fileIoUrl = "fileIo_service"

  implicit val actorSystem: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "actorSystem");
  implicit val executionContext: ExecutionContextExecutor = actorSystem.executionContext

  val jsonFileIO = new fileJsonImpl.FileIO()
  val xmlFileIO = new fileXmlImpl.FileIO()

  val routeSave: (String, String => Unit) => Route = (format, saveOperation) =>
    path("fileIO" / format / "save") {
      put {
        entity(as[String]) {
          playfield =>
            saveOperation(playfield)
            complete(HttpResponse.apply(StatusCodes.OK))
        }
      }
    }

  println("New Server for FileIO started at http://" + fileIoUrl + ":" + port + "\n Press RETURN to stop...")
  Http().newServerAt(fileIoUrl, port).bind(
    concat(
      routeSave("Json", jsonFileIO.save),
      routeSave("xml", xmlFileIO.save),
      path("fileIO" / "load" / Remaining) {
        filename =>
          get {
            filename match {
              case "json" => complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, jsonFileIO.load()))
              case "xml" => complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, xmlFileIO.load()))
              case _ => complete(HttpResponse(StatusCodes.BadRequest))
            }
          }
      }
    )
  )
}
