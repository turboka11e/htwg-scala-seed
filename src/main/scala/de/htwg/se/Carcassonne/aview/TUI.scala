package de.htwg.se.Carcassonne.aview

import de.htwg.se.Carcassonne.controller.Controller
import de.htwg.se.Carcassonne.model.{Area, Card, CardCreator, Grid, Matrix, Player, Points}
import de.htwg.se.Carcassonne.util.Observer

class TUI(controller: Controller) extends Observer {

  controller.add(this)

  def processInputLine(input: String): Unit = {
    input match {
      case "q" =>
      case "new" => controller.newGame()
      case "y" => controller.decide(true)
      case "n" => controller.decide(false)
      case "r" => controller.rotateRight()
      case "l" => controller.rotateLeft()
      case _ => validateLongString(input)
    }
  }

  def validateLongString(input: String): Unit = {
    if (input.forall(p => p.isDigit || p == ' ') && input.nonEmpty) {
      val extract = input.split(" ")
      extract.length match {
        case 1 => forkDigit(extract.head.toInt)
        case 2 => controller.placeCard(extract(0).toInt, extract(1).toInt)
        case _ =>
      }
    } else if (input.nonEmpty) {
      controller.addPlayer(input)
    }
  }

  def forkDigit(input:Int):Unit = {
    controller.getGameState match {
      case 0 => controller.createGrid(input)
      case 4 => controller.selectArea(input)
      case _ =>
    }
  }


  override def update: Unit = print(controller.playFieldToString)

}
