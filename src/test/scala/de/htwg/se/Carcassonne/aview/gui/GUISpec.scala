package de.htwg.se.Carcassonne.aview.gui

import de.htwg.se.Carcassonne.Carcassonne
import org.scalatest.{Matchers, WordSpec}

class GUISpec extends WordSpec with Matchers {
  "An StartGui" when {
    "when the Game is started" should {
      val newGame = Carcassonne
      "go through almost everything" in {
        newGame.tui.processInputLine("new")
        newGame.tui.processInputLine("3")
        newGame.tui.processInputLine("Lukas")
        newGame.tui.processInputLine("n")
        newGame.controller.firstCard(5)
        newGame.tui.processInputLine("ldkjflkjasd")
        newGame.tui.processInputLine("r")
        newGame.tui.processInputLine("l")
        newGame.tui.processInputLine("l")
        newGame.tui.processInputLine("0")
        newGame.tui.processInputLine("1 1")
        newGame.tui.processInputLine("new")
      }
    }
  }
}
