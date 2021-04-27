package de.htwg.se.Carcassonne.controller

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.unmarshalling.Unmarshal
import de.htwg.se.Carcassonne.TUI.controller
import de.htwg.se.Carcassonne.controller.controllerRestImpl._

import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, ExecutionContextExecutor, Future}
import scala.swing.Reactor
import scala.swing.event.Event

object RestControllerTUI extends Reactor {

  listenTo(controller)

  implicit val actorSystem: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "actorSystem");
  implicit val executionContext: ExecutionContextExecutor = actorSystem.executionContext

  def port = 8090

  def rootUrl = "root_service"
  def tuiUrl = "tui_service"

  def receivePOSTAndPublishEvent(eventPath: String, event: String => Event): Route = {
    path("tui" / "events" / eventPath) {
      post {
        entity(as[String]) { playfield =>
          controller.publish(event(playfield))
          complete(HttpResponse.apply(StatusCode.int2StatusCode(200)))
        }
      }
    }
  }

  def receiveGETAndPublishEvent(eventPath: String, event: Event): Route = {
    path("tui" / "events" / eventPath) {
      get {
        controller.publish(event)
        complete(HttpResponse.apply(StatusCode.int2StatusCode(200)))
      }
    }
  }

  def startServer(): Future[Http.ServerBinding] = {
    Http().newServerAt(tuiUrl, port).bind(
      concat(
        receivePOSTAndPublishEvent("playfieldChanged", field => new PlayfieldChanged(field)),
        receivePOSTAndPublishEvent("setGrid", field => new SetGrid(field)),
        receivePOSTAndPublishEvent("addPlayer", field => new AddPlayers(field)),
        receivePOSTAndPublishEvent("firstCard", field => new FirstCard(field)),
        receiveGETAndPublishEvent("gameOver", new GameOverTUI),
        receiveGETAndPublishEvent("invalidInputString", new InvalidInputString)
      )
    )
  }

  def stopServer(server: Future[Http.ServerBinding]): Unit = {
    server
      .flatMap(_.unbind()).onComplete(_ => println(port + " released"))
  }

  def sendGET_noParam(commandPath: String): Unit = {
    Http().singleRequest(
      HttpRequest(
        method = HttpMethods.GET,
        uri = "http://" + rootUrl + ":8080/controller/" + commandPath,
      )
    )
  }

  def sendGET_withParam(commandPath: String, param: String): Unit = {
    Http().singleRequest(
      HttpRequest(
        method = HttpMethods.GET,
        uri = "http://" + rootUrl + ":8080/controller/" + commandPath + "/" + param
      )
    )
  }

  def sendGET_withQuery(commandPath: String, x: Int, y: Int): Unit = {
    Http().singleRequest(
      HttpRequest(
        method = HttpMethods.GET,
        uri = "http://" + rootUrl + ":8080/controller/" + commandPath + "?x=" + x + "&y=" + y
      )
    )
  }

  def sendAndGET_noParam(commandPath: String): Int = {
    val future = Http().singleRequest(
      HttpRequest(
        method = HttpMethods.GET,
        uri = "http://" + rootUrl + ":8080/controller/" + commandPath
      )
    )

    val result: Future[String] = future.flatMap {
      case HttpResponse(StatusCodes.OK, _, e, _) =>
        Unmarshal(e).to[String]
      case HttpResponse(status, _, entity, _) =>
        entity.discardBytes()
        Future.failed(new Exception(s"service returned ${status.intValue()}"))
    }

    Await.result(result, 2.second).toInt
  }

}
