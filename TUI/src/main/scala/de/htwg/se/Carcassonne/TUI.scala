package de.htwg.se.Carcassonne

import de.htwg.se.Carcassonne.aview.tui.TUI
import de.htwg.se.Carcassonne.controller.controllerRestImpl.Controller
import de.htwg.se.Carcassonne.controller.{ControllerInterface, RestControllerTUI}

import scala.io.StdIn.readLine


object TUI {

  val controller: ControllerInterface = new Controller
  val tui = new TUI(controller)
  val restController = RestControllerTUI


  def main(args: Array[String]): Unit = {

    val server = restController.startServer()

    var input: String = ""
    println("Neues Spiel mit 'new' starten.")
    do {
      input = readLine()
      tui.processInputLine(input)
    } while (input != "q")

    restController.stopServer(server)

  }

}
