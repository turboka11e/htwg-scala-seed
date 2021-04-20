package de.htwg.se.Carcassonne.model.fileComponent

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal

import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, ExecutionContextExecutor, Future}

object RestControllerFileRoot {

  implicit val actorSystem: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "actorSystem");
  implicit val executionContext: ExecutionContextExecutor = actorSystem.executionContext

  def saveFile(fileFormat: String, text: String) = {
    Http().singleRequest(
      HttpRequest(
        method = HttpMethods.PUT,
        uri = "http://localhost:8085/fileIO/" + fileFormat + "/save",
        entity = HttpEntity(ContentTypes.`text/html(UTF-8)`, text)
      )
    )
  }

  def saveJsonFile(text: String): Unit = {
    saveFile("Json", text)
  }

  def saveXmlFile(text: String): Unit = {
    saveFile("xml", text)
  }

  def loadFile(format: String): String = {
    val future = Http().singleRequest(
      HttpRequest(
        method = HttpMethods.GET,
        uri = "http://localhost:8085/fileIO/load/" + format
      )
    )

    val result: Future[String] = future.flatMap {
      case HttpResponse(StatusCodes.OK, _, e, _) =>
        Unmarshal(e).to[String]
      case HttpResponse(status, _, entity, _) =>
        entity.discardBytes()
        Future.failed(new Exception(s"service returned ${status.intValue()}"))
    }

    Await.result(result, 2.second)
  }

}
