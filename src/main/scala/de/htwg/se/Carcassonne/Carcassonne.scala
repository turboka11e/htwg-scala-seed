package de.htwg.se.Carcassonne

import de.htwg.se.Carcassonne.aview.TUI
import de.htwg.se.Carcassonne.model.{Area, Card, Grid, Player}

import scala.io.StdIn.readLine

object Carcassonne {

  var grid = new Grid(5)
  val TUI = new TUI

  def main(args: Array[String]): Unit = {
    var input: String = ""

    do {
      println("Grid : " + grid.toString)
      input = readLine()
      grid = TUI.processInputLine(input, grid)
    } while (input != "q")
  }
}

