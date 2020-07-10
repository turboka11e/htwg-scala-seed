package de.htwg.se.Carcassonne.aview.tui

import de.htwg.se.Carcassonne.controller.controllerComponent.{ControllerInterface, GameStatus}
import de.htwg.se.Carcassonne.controller.controllerComponent.controllerBaseImpl.{AddPlayers, Controller, GameOver, NewGame, PlayfieldChanged, RotateR, SetGrid}

import scala.swing.Reactor

class TUI(controller: ControllerInterface) extends Reactor {

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

  def decide(dc: Boolean): Unit = {
    controller.getGameState match {
      case 2 =>
        if (!dc) {
          controller.firstCard()
        } else {
          controller.changeGameState(1)
        }
      case 3 => if (dc) controller.changeGameState(4)

      case 4 => if (!dc) controller.changeGameState(5)

      case _ =>
    }
  }


  def validateLongString(input: String): Unit = {
    if (input.forall(p => p.isDigit || p == ' ') && input.nonEmpty) {
      val extract = input.split(" ")
      extract.length match {
        case 1 => forkDigit(extract.head.toInt)
        case 2 => controller.placeCard(extract(0).toInt, extract(1).toInt)
          controller.placeAble()
        case _ =>
      }
    } else if (input.nonEmpty) {
      controller.addPlayer(input)
    }
  }

  def forkDigit(input: Int): Unit = {
    controller.getGameState match {
      case 0 => controller.createGrid(input)
      case 4 => controller.selectArea(input)
      case _ =>
    }
  }

  reactions += {
    case event: NewGame => printTui()
    case event: SetGrid => printTui()
    case event: AddPlayers => printTui()
    case event: PlayfieldChanged => printTui()
    case event: RotateR => printTui()
    case event: GameOver => printTui()
  }

  def printTui(): Unit = {
//    println(controller.statusText)
    print(controller.playFieldToString)
  }

}
