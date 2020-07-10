package de.htwg.se.Carcassonne.controller

import de.htwg.se.Carcassonne.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.Carcassonne.model._
import de.htwg.se.Carcassonne.model.playfieldComponent.playfieldBaseImpl.Playfield
import org.scalatest.{Matchers, WordSpec}

class ControllerSpec extends WordSpec with Matchers {
  "A Controller" when {
    "A Controller" when {
      "empty" should {
        val play = Playfield()
        val controller = new Controller(play)
        "handle undo/redo of solving a grid correctly" in {
          controller.newGame()
          controller.getGameState should be(0)
        }
      }
    }
  }
}


