package de.htwg.se.Carcassonne.aview

import de.htwg.se.Carcassonne.model.{Area, Card, CardCreator, Grid, GridCreator, Matrix, Player, Territories}

class TUI {

  val greetings: String = "WELCOME TO CARCASSONNE.\n"
  val help: String = "Help messages"

  def welcomeMessage: String = {
    greetings
  }

  def processGridSize(size: Int): GridCreator = {
    new GridCreator(size)
  }

  def processAddingPlayer(name: String, gridCreator: GridCreator): GridCreator = {
    gridCreator.addPlayer(name)
  }

  def processRotateCard(input:String, freshCard: CardCreator):CardCreator = {
    if(input == "r"){
      print(freshCard.rotateRight.showCard)
      freshCard.rotateRight
    } else if (input == "l"){
      print(freshCard.rotateLeft.showCard)
      freshCard.rotateLeft
    } else {
      freshCard
    }
  }

  def processSelectArea(input:String, freshCard:CardCreator):CardCreator = {
    freshCard.setPlayerToArea(input.toInt)
  }

  def processPlacingCard(input:Array[String], grid: Grid, freshCard:CardCreator):Grid = {
    val x = input.apply(0).toInt
    val y = input.apply(1).toInt

    grid.place(x, y, freshCard.finalCard)

  }

}
