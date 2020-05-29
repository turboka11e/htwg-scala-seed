package de.htwg.se.Carcassonne.aview

import de.htwg.se.Carcassonne.controller.Controller
import de.htwg.se.Carcassonne.model.{Area, Card, CardCreator, Grid, Matrix, Player, Territories}
import de.htwg.se.Carcassonne.util.Observer

class TUI(controller: Controller) extends Observer {

  controller.add(this)

  def processInputLine(input:String):Unit = {
    input match {
      case "q" =>
      case "new" => controller.newGame()
      case "y" => controller.decide(true)
      case "n" => controller.decide(false)
      case "r" =>
      case "l" =>
      case _ => validateLongString(input)
    }
  }
  def validateLongString(input:String):Unit = {
    if(input.forall(p => p.isDigit)){
      val extract = input.split(" ").map(c => c.toInt)
      extract.length match {
        case 1 => controller.forkDigit(extract.head)
        case 2 =>
      }
    } else {
      controller.addPlayer(input)
    }

  }


  override def update: Unit = print(controller.playFieldToString)

}
