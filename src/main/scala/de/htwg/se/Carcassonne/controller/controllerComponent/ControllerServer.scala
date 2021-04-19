package de.htwg.se.Carcassonne.controller.controllerComponent

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import de.htwg.se.Carcassonne.Carcassonne.controller
import de.htwg.se.Carcassonne.controller.controllerComponent.controllerBaseImpl.PlayfieldChanged

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.swing.Reactor

object ControllerServer extends Reactor {

  listenTo(controller)

  def port = 8080

  def url = "localhost"

  implicit val actorSystem: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "actorSystem");
  implicit val executionContext: ExecutionContextExecutor = actorSystem.executionContext

  def startServer(): Future[Http.ServerBinding] = {
    val route =
      concat(
        path("controller" / "newGame") {
          get {
            controller.newGame()
            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, controller.statusText))
          }
        },
        path("controller" / "createGrid" / IntNumber) {
          gridSize =>
            get {
              controller.createGrid(gridSize)
              complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, controller.statusText))
            }
        },
        path("controller" / "addPlayer") {
          parameter('player.name) {
            playerName =>
              get {
                controller.addPlayer(playerName)
                complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, controller.statusText))
              }
          }
        },
        path("controller" / "firstCard") {
          get {
            controller.firstCard()
            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, controller.statusText))
          }
        },
        pathPrefix("controller" / "firstCard" / IntNumber) {
          select => {
            get {
              controller.firstCard(select)
              complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, controller.statusText))
            }
          }
        },
      )
    Http().newServerAt(url, port).bind(route)
  }

  def stopServer(server: Future[Http.ServerBinding]): Unit = {
    server
      .flatMap(_.unbind()).onComplete(_ => println(port + " released"))
  }

  reactions += {
    //    case event: SetGrid => printTui()
    //    case event: AddPlayers => printTui()
    //    case event: FirstCard => printTui()
    case event: PlayfieldChanged => println("test")
    //    case event: GameOver => println("GAME OVER")
    //    case event: InvalidInputString => printErrorTUI()
  }

}
