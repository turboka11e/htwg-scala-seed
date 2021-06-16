package de.htwg.se.Carcassonne.aview.tui

import de.htwg.se.Carcassonne.controller._
import de.htwg.se.Carcassonne.controller.controllerComponent.{AddPlayers, Controller, NewGame, PlayfieldChanged, RotateR, SetGrid}
import de.htwg.se.Carcassonne.controller.GameState._

import scala.swing.Reactor

class TUI(controller: Controller) extends Reactor {

  listenTo(controller)

  def processInputLine(input: String): Unit = {
    input match {
      case "q" =>
      case "new" => controller.newGame()
      case "y" => decide(true)
      case "n" => decide(false)
      case "r" => controller.rotateRight()
      case "l" => controller.rotateLeft()
      case "redo" => controller.redo()
      case "undo" => controller.undo()
      case _ => validateLongString(input)
    }
  }

  def decide(dc:Boolean):Unit = {
    controller.getGameState match {
      case AddPlayer =>
        if(!dc){
          controller.firstCard()
        } else {
          controller.changeGameState(GameState.NewGame)
        }
      case DrawCard => if(dc) controller.changeGameState(SelectArea)

      case SelectArea => if(!dc) controller.changeGameState(PlaceCard)

      case _ =>
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
      case EmptyGame => controller.createGrid(input)
      case SelectArea => controller.selectArea(input)
      case _ =>
    }
  }

  reactions += {
    case event: NewGame => print(controller.playFieldToString)
    case event: SetGrid => print(controller.playFieldToString)
    case event: AddPlayers => print(controller.playFieldToString)
    case event: PlayfieldChanged => print(controller.playFieldToString)
    case event: RotateR => print(controller.playFieldToString)
  }

}
