package de.htwg.se.Carcassonne

import de.htwg.se.Carcassonne.aview.TUI
import de.htwg.se.Carcassonne.aview.gui.{StartGUI, SwingGui}
import de.htwg.se.Carcassonne.controller.{Controller, NewGame}
import de.htwg.se.Carcassonne.model.{Area, Card, CardManipulator, Grid, Player, Playfield}

import scala.io.StdIn._

object Carcassonne {

  val controller = new Controller(new Playfield)
  val tui = new TUI(controller)
  val gui = new StartGUI(controller)

  controller.publish(new NewGame)

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
