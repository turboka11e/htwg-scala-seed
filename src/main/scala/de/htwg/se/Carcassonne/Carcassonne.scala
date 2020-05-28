package de.htwg.se.Carcassonne

import de.htwg.se.Carcassonne.aview.TUI
import de.htwg.se.Carcassonne.model.{Area, Card, Grid, Player}

import scala.io.StdIn.readLine

object Carcassonne {

  var grid = new Grid(5)
  var card = Card()
  val TUI = new TUI

  def main(args: Array[String]): Unit = {
    var input: String = ""
    var inputX: String = ""
    var inputY: String = ""

    do {
      println("Hello")
      TUI.processPlayerNumber(input)
      input = readLine()
      inputX = readLine()
      inputY = readLine()
      grid = TUI.processInputGrid(inputX, inputY, grid, card)
    } while (input != "q")
  }
}

