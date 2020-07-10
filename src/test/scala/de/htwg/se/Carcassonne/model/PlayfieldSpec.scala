package de.htwg.se.Carcassonne.model

import de.htwg.se.Carcassonne.model.gridComponent.gridBaseImpl.Grid
import de.htwg.se.Carcassonne.model.playerComponent.Player
import de.htwg.se.Carcassonne.model.playfieldComponent.playfieldBaseImpl.{Playfield, RawCardFactory}
import de.htwg.se.Carcassonne.model.playfieldComponent.playfieldBaseImpl
import org.scalatest._

class PlayfieldSpec extends WordSpec with Matchers {

  "A Playfield" when {
    "new" should {
      val p1 = Playfield()
      "show Game state" in {
        p1.getGameState should be(0)
      }
      "show Success" in {
        p1.getSuccess should be(true)
      }
      "change game state to 1" in {
        p1.changeGameState(1) should be(Playfield(gameState = 1))
      }
      "allow to set field Size" in {
//        p1.fieldSize(3) should be(Playfield(grid = new Grid(3), gameState = 1))
      }
      "allow to set next Player" in {
        p1.nextPlayer should be(Playfield(isOn = 1))
      }
    }
    "new with field size 3 set" should {
      val p2 = Playfield().fieldSize(3)
      "allow to add player" in {
        p2.addPlayer("Test") should be(playfieldBaseImpl.Playfield(grid = new Grid(3), gameState = 2, players = Nil ::: List(Player("Test"))))
      }
    }
    "new with field size 3 and one player added" should {
      val p3 = Playfield().fieldSize(3).addPlayer("Test")
      "return fresh Card" in {
        p3.getFreshCard(0) should be(playfieldBaseImpl.Playfield(grid = new Grid(3),
          gameState = 2, players = Nil ::: List(Player("Test")), freshCard = RawCardFactory("selectCard", 0, 0).drawCard()))
      }
    }
    "new with field size 3 and one player addes and fresh Card" should {
      val p4 = Playfield().fieldSize(3).addPlayer("Test").getFreshCard(0)
      "rotate freshCard to right" in {
        p4.rotateR should be(playfieldBaseImpl.Playfield(grid = new Grid(3),
          gameState = 2, players = Nil ::: List(Player("Test")), freshCard = RawCardFactory("selectCard", 0, 0).drawCard().rotateRight))
      }
      "rotate freshcard to left" in {
        p4.rotateL should be(playfieldBaseImpl.Playfield(grid = new Grid(3),
          gameState = 2, players = Nil ::: List(Player("Test")), freshCard = RawCardFactory("selectCard", 0, 0).drawCard().rotateLeft))
      }
      "allow selections of an area" in {
        p4.selectArea(0) should be(playfieldBaseImpl.Playfield(grid = new Grid(3),
          gameState = 5, players = Nil ::: List(Player("Test")), freshCard = RawCardFactory("selectCard", 0, 0).drawCard().setPlayerToArea(0)))
      }
      "allow placement of Card in Grid" in {
        p4.placeCard(1, 1) should be(p4.placeCard(1, 1))
      }

    }
  }


}