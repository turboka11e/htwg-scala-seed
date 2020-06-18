package de.htwg.se.Carcassonne.aview

import de.htwg.se.Carcassonne.controller.Controller
import de.htwg.se.Carcassonne.model.{CardManipulator, Playfield}
import org.scalatest._

class TUISpec extends WordSpec with Matchers {

  "A Carcasonne TUI" should  {
    val controller = new Controller(Playfield())
    val notChangedController = new Controller(Playfield())
    val playfield = Playfield().fieldSize(3)
    val tui = new TUI(controller)
    "create a new game on input 'new'" in {
      tui.processInputLine("new")
      controller.playfield should be(Playfield())
    }
    "create Gamefield of size 3 on input 3" in {
      tui.forkDigit(3)
      controller.playfield should be(playfield)
    }
    "decide on the gamestate when input 'y'" in {
      tui.processInputLine("y")
      controller.playfield should be(playfield)
    }
    "decide on the gamestate when input 'n" in {
      tui.processInputLine("n")
      controller.playfield should be(playfield)
    }
    "rotates the card right when input 'r'" in {
      tui.processInputLine("r")
      controller.playfield should be(playfield.rotateR)
    }
    "rotates the card left when input 'l'" in {
      tui.processInputLine("l")
      controller.playfield should be (controller.playfield)
    }
    "validates the input '_'" in {
      tui.processInputLine("_")
      tui.validateLongString("")
    }

  }

}
