package de.htwg.se.Carcassonne

import de.htwg.se.Carcassonne.aview.gui.{StartGUI, SwingGui}
import de.htwg.se.Carcassonne.aview.tui.TUI
import de.htwg.se.Carcassonne.controller.controllerComponent.{Controller, NewGame}
import de.htwg.se.Carcassonne.model.playfieldComponent.playfieldBaseImpl.Playfield

import scala.io.StdIn._

object Carcassonne {

  val controller = new Controller(new Playfield)
  val tui = new TUI(controller)
  val gui = new StartGUI(controller)

  //controller.publish(new NewGame)

  def main(args: Array[String]): Unit = {
    var input: String = ""
    if(args.length > 0) input = args(0)

    if (!input.isEmpty){
      tui.processInputLine(input)
    }
    else {
      do {
        input = readLine()
        tui.processInputLine(input)
      } while (input != "q")
    }

  }
}
