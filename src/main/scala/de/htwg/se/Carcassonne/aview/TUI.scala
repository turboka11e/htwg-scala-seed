package de.htwg.se.Carcassonne.aview

import de.htwg.se.Carcassonne.model.{Area, Card, Grid, Matrix, Player, Territories}

class TUI {

  val greetings: String = "WELCOME TO THE EXPIERECNE THAT IS CARCASONNE."
  val help: String = "Help messages"

  def welcomeMessageHelp: String = {
    greetings
  }

  def processPlayerNumber(input: String): Int = {
    input match {
      case _ => input.toInt
    }
  }

  def processPlayerName(input: String): Player = { //PlayerOverview dann reinspeihern
    if (processPlayerNumber(input.toString) != 0) {
      val Player1 = input.
    }
  }

   def processInputGrid(inputX: String, inputY: String, grid:Grid, card: Card):Grid = {
     (inputX, inputY) match {
       case ("","") => grid
       case (_,"0") => new Grid(inputX.toInt)
       case (_,_) => grid.set(inputX.toInt, inputY.toInt, card)
         if (grid.checkSet(inputX.toInt, inputY.toInt, card) != 0)


     }
   }

  def processInputCards(input: String, card: Card):Card = {
      input match {
        case "c" => card
        case "rotate" => card.rotateRight()
      }
  }

}
