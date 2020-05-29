package de.htwg.se.Carcassonne

import de.htwg.se.Carcassonne.aview.TUI
import de.htwg.se.Carcassonne.controller.Controller
import de.htwg.se.Carcassonne.model.{Area, Card, CardCreator, Grid, Player, Playfield}

import scala.io.StdIn._
import Console.{GREEN, RED, RESET, UNDERLINED, YELLOW_B}

object Carcassonne {

  val controller = new Controller(new Playfield)
  val tui = new TUI(controller)
  //controller.notifyObservers

  def main(args: Array[String]): Unit = {
    var input: String = ""

    print("Welcome to Carcassonne\nNeues Spiel mit 'new' starten.\n")
    controller.notifyObservers

    do {
      input = readLine()
      tui.processInputLine(input)
    } while (input != "q")

  }
}
