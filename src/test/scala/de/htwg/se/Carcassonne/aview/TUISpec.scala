package de.htwg.se.Carcassonne.aview

import de.htwg.se.Carcassonne.controller.Controller
import de.htwg.se.Carcassonne.model.Playfield
import org.scalatest._

class TUISpec extends WordSpec with Matchers {

  "A Carcasonne TUI" should  {
    val controller = new Controller(Playfield())
    val tui = new TUI(controller)
    "create a new game on input 'new'" in {
      tui.processInputLine("new")
      controller.playfield should be(Playfield())
    }
    "decide on the gamestate when input 'y'" in {
      tui.processInputLine("y")
      controller.decide(true) should be(Playfield())
    }
    "decide on the gamestate when input 'n" in {
      tui.processInputLine("n")
      controller.decide(false) should be(Playfield().changeGameState(2))
    }
    "rotates the card right when input 'r'" in {
      tui.processInputLine("r")
      controller.rotateRight() should be()
    }
    "rotates the card left when input 'l'" in {
      tui.processInputLine("l")
      controller.rotateLeft() should be ()
    }
    "validtes the input '_'" in {
      tui.processInputLine("_")
      tui.validateLongString("")
    }
  }

}
