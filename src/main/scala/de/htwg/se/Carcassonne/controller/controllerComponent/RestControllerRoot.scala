package de.htwg.se.Carcassonne.controller.controllerComponent

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.{Http, server}
import de.htwg.se.Carcassonne.Carcassonne.controller
import de.htwg.se.Carcassonne.aview.tui.PrettyPrint
import de.htwg.se.Carcassonne.controller.controllerComponent.controllerBaseImpl._

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.swing.Reactor

object RestControllerRoot extends Reactor {

  listenTo(controller)

  def port = 8080

  def rootUrl = "root_service"
  def tuiUrl = "tui_service"

  implicit val actorSystem: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "actorSystem");
  implicit val executionContext: ExecutionContextExecutor = actorSystem.executionContext

  def pathNoParamPartial(basePath: String, body: () => String)(command: String, execution: () => Unit): Route = {
    path(basePath / command) {
      get {
        execution()
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, body()))
      }
    }
  }

  def pathWithSingleIntParamPartial(basePath: String)(command: String, execution: Int => Unit, body: () => String): Route = {
    path(basePath / command / IntNumber) {
      parameter =>
        get {
          execution(parameter)
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, body()))
        }
    }
  }

  def commandNoPara: (String, () => Unit) => server.Route = pathNoParamPartial("controller", () => controller.statusText)

  def commandWithSingleNumberParam(command: String, execution: Int => Unit, body: () => String): Route = {
    pathWithSingleIntParamPartial("controller")(command, execution, body)
  }

  def startServer(): Future[Http.ServerBinding] = {
    val route =
      concat(
        commandNoPara("newGame", () => controller.newGame()),
        commandWithSingleNumberParam("createGrid", gridSize => controller.createGrid(gridSize), () => controller.statusText),
        path("controller" / "addPlayer" / Remaining) {
          playerName =>
            get {
              controller.addPlayer(playerName)
              complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, controller.statusText))
            }
        },
        commandNoPara("firstCard", () => controller.firstCard()),
        commandWithSingleNumberParam("firstCard", select => controller.firstCard(select), () => controller.statusText),
        commandWithSingleNumberParam("changeGameState", state => controller.changeGameState(state), () => controller.statusText),
        pathNoParamPartial("controller", () => controller.getGameState.toString)("getGameState", () => ()),
        commandNoPara("rotateLeft", () => controller.rotateLeft()),
        commandNoPara("rotateRight", () => controller.rotateRight()),
        commandWithSingleNumberParam("selectArea", area => controller.selectArea(area), () => controller.statusText),
        path("controller" / "placeCard") {
          parameter('x.as[Int], 'y.as[Int]) {
            (x, y) =>
              get {
                controller.placeCard(x, y)
                controller.placeAble()
                complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, controller.statusText))
              }
          }
        },
        commandNoPara("placeAble", () => controller.placeAble()),
        commandNoPara("undo", () => controller.undo()),
        commandNoPara("redo", () => controller.redo()),
        commandNoPara("save", () => controller.save()),
        commandNoPara("load", () => controller.load()),
      )
    Http().newServerAt(rootUrl, port).bind(route)
  }

  def stopServer(server: Future[Http.ServerBinding]): Unit = {
    server
      .flatMap(_.unbind()).onComplete(_ => println(port + " released"))
  }

  reactions += {
    case event: SetGrid => sendPOSTEvent("setGrid")
    case event: AddPlayers => sendPOSTEvent("addPlayer")
    case event: FirstCard => sendPOSTEvent("firstCard")
    case event: PlayfieldChanged => sendPOSTEvent("playfieldChanged")
    case event: GameOver => sendGETEvent("gameOver")
    case event: InvalidInputString => sendGETEvent("invalidInputString")
  }

  def sendPOSTEvent(gameEvent: String): Unit = {
    Http().singleRequest(
      HttpRequest(
        method = HttpMethods.POST,
        uri = "http://" + tuiUrl + ":8090/tui/events/" + gameEvent,
        entity =
          HttpEntity(ContentTypes.`text/plain(UTF-8)`, new PrettyPrint(controller.playfield).printo())
      )
    )
  }

  def sendGETEvent(gameEvent: String): Unit = {
    Http().singleRequest(
      HttpRequest(
        method = HttpMethods.GET,
        uri = "http://" + tuiUrl + ":8090/tui/events/" + gameEvent
      )
    )
  }
}
