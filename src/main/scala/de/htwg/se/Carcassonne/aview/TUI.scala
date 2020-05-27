package de.htwg.se.Carcassonne.aview

import de.htwg.se.Carcassonne.model.{Area, Card, Grid, Matrix, Player, Territories}

class TUI {

  val greetings: String = "WELCOME TO THE EXPIERECNE THAT IS CARCASONNE."
  val help: String = "Help messages"

  def welcomeMessageHelp: String = {
    greetings
  }

  def processPlayerNumber(input1: String): Int = {
    println("Wie viele Spieler gibt es?")
    input1 match {
      case _ => input1.toInt
    }
  }

  def processPlayerName(input: String): Player = { //PlayerOverview dann reinspeihern
    if (processPlayerNumber(input.toString) != 0) {
      val Player1 = input.
    }
  }

   def processInputGrid(input1: String, input2: String, grid:Grid, card: Card):Grid = {
     (input1, input2) match {
       case ("","") => grid
       case (_,"0") => new Grid(input1.toInt)
       case (_,_) => grid.set(input1.toInt, input2.toInt, card)
         if (grid.checkSet(input1.toInt, input2.toInt, card) != 0)


     }
   }

  def processInputCards(input: String, card: Card):Card = {
      input match {
        case "c" => card
        case "rotate" => card.rotateRight()
      }
  }

}
