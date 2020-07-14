package de.htwg.se.Carcassonne.aview

import de.htwg.se.Carcassonne.Carcassonne
import org.scalatest.{Matchers, WordSpec}

class GUISpec extends WordSpec with Matchers {
  "An StartGui" when {
    "when the Game is started" should {
      val newGame = Carcassonne
      newGame.tui.processInputLine("new")
      newGame.tui.processInputLine("3")
      newGame.tui.processInputLine("Lukas")
      newGame.tui.processInputLine("n")
      newGame.tui.processInputLine("r")
      newGame.tui.processInputLine("l")
      newGame.tui.processInputLine("0")
      newGame.tui.processInputLine("0 0")
      "" in {

      }
    }
  }
}
