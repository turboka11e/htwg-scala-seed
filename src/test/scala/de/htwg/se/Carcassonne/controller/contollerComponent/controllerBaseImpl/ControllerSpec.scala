package de.htwg.se.Carcassonne.controller.contollerComponent.controllerBaseImpl

import de.htwg.se.Carcassonne.controller.controllerComponent.GameStatus
import de.htwg.se.Carcassonne.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.Carcassonne.model.playfieldComponent.playfieldBaseImpl.Playfield
import org.scalatest.{Matchers, WordSpec}

class ControllerSpec extends WordSpec with Matchers {
  "A Controller" when {
    "A Controller" when {
      "initilised" should {
        val play = Playfield()
        val controller = new Controller(play)
        "create new Game" in {
          controller.newGame()
          controller.gameStatus should be(GameStatus.NEW)
        }
        "return logical GameState" in {
          controller.getGameState should be(0)
        }
        "create Grid" in {
          controller.createGrid(2)
          controller.gameStatus should be(GameStatus.RESIZE)
        }
        "add Player" in {
          controller.addPlayer("Klaus")
          controller.gameStatus should be(GameStatus.PLAYER)
        }
        "draw random first Card" in { // actual method in game
          controller.firstCard()
          controller.gameStatus should be(GameStatus.FIRSTCARD)
        }
        "draw selected first Card" in { // only for testing
          controller.firstCard(14)
          controller.gameStatus should be(GameStatus.FIRSTCARD)
        }
        "rotate freshCard" in {
          controller.rotateLeft()
          controller.gameStatus should be(GameStatus.ROTATE)
          controller.rotateRight()
          controller.gameStatus should be(GameStatus.ROTATE)
        }
        "select Ared on freshCard" in {
          controller.selectArea(0)
          controller.gameStatus should be(GameStatus.MANICAN)
        }
        "place first Card" in {
          controller.placeCard(0, 0)
          controller.gameStatus should be(GameStatus.PLACE)
        }
        "handle undo/redo of solving a grid correctly" in {
          controller.undo()
          controller.gameStatus should be(GameStatus.UNDO)
          controller.redo()
          controller.gameStatus should be(GameStatus.REDO)
        }
        "save and load" in {
          controller.save()
          controller.gameStatus should be(GameStatus.SAVE)
          controller.load()
          controller.gameStatus should be(GameStatus.LOADED)
        }
        "Game over" in {
          controller.firstCard(14) // only for testing
          controller.placeCard(0, 1)
          controller.firstCard(14)  // only for testing
          controller.placeCard(1, 0)
          controller.firstCard(14)  // only for testing
          controller.placeCard(1, 1)
          controller.placeAble()
          controller.gameStatus should be(GameStatus.GAMEOVER)
        }
      }
    }
  }
}


