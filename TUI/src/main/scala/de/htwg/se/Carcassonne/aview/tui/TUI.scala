package de.htwg.se.Carcassonne.aview.tui

import de.htwg.se.Carcassonne.controller.ControllerInterface
import de.htwg.se.Carcassonne.controller.controllerRestImpl.{AddPlayers, FirstCard, GameOverTUI, InvalidInputString, PlayfieldChanged, SetGrid}

import scala.swing.Reactor
import scala.util.{Failure, Success, Try}

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
        case 1 => Try(Integer.parseInt(extract.head)) match {
          case Success(digit) => forkDigit(digit)
          case Failure(exception) => println("Could not parse integer! {}", exception);
        }
        case 2 => Try((extract(0).toInt, extract(1).toInt)) match {
          case Success((x, y)) =>
            controller.placeCard(x, y)
//            controller.placeAble()
          case Failure(exception) => println("Could not parse integer! Error: {}", exception);
        }
        case _ =>
      }
    } else if (input.nonEmpty) {
      if (controller.getGameState == 1) {
        controller.addPlayer(input)
      } else {
        if (controller.getGameState > 1) controller.publish(new InvalidInputString)
      }
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
    case event: SetGrid => print(event.playfield())
    case event: AddPlayers => print(event.playfield())
    case event: FirstCard => print(event.playfield())
    case event: PlayfieldChanged => print(event.playfield())
    case event: GameOverTUI => println("GAME OVER")
    case event: InvalidInputString => print(Console.RED + "*** " + "Ungültige Eingabe! Den Anweisungen folgen!" + " ***" + Console.RESET)
  }

}
