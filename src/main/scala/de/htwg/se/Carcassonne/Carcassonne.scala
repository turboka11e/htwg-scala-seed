package de.htwg.se.Carcassonne

import com.google.inject.{Guice, Injector}
import de.htwg.se.Carcassonne.aview.gui.StartGUI
import de.htwg.se.Carcassonne.aview.tui.TUI
import de.htwg.se.Carcassonne.controller.controllerComponent.{ControllerInterface, ControllerServer}

import scala.io.StdIn._

object Carcassonne {

  val injector: Injector = Guice.createInjector(new CarcassonneModule)
  val controller: ControllerInterface = injector.getInstance(classOf[ControllerInterface])
  val tui = new TUI(controller)
  val gui = new StartGUI(controller)
  val controllerServer = ControllerServer

  def main(args: Array[String]): Unit = {

    val server = controllerServer.startServer()

    var input: String = ""
      println("Neues Spiel mit 'new' starten.")
      do {
        input = readLine()
        tui.processInputLine(input)
      } while (input != "q")

    controllerServer.stopServer(server)
  }
}
