package de.htwg.se.Carcassonne.controller


import de.htwg.se.Carcassonne.model._
import de.htwg.se.Carcassonne.util.Observer
import org.scalatest.{Matchers, WordSpec}
/*
class ControllerSpec extends WordSpec with Matchers {
  "A Controller" when {
    "observed by an Observer" should {
      val smallGrid = new Grid(4)
      val controller = new Controller(new Playfield)
      val g1 = Playfield(grid = new Grid(3), gameState = 1)
      val g2 = Playfield(grid = new Grid(3), gameState = 1, players = List(Player("Test")))
      val g3 = Playfield(grid = new Grid(3), gameState = 3, players = List(Player("Test")),
        freshCard = RawCardFactory("selectCard", 0, 14).drawCard())
      val g4 = Playfield(grid = new Grid(3).place(1, 1, RawCardFactory("selectCard", 0, 14).drawCard().finalCard(1, 1)),
        gameState = 3, players = List(Player("Test")), freshCard = RawCardFactory("selectCard", 0, 14).drawCard())
      val observer = new Observer {
        var updated: Boolean = false
        def isUpdated: Boolean = updated
        override def update: Boolean = {updated = true; updated}
      }
      controller.add(observer)
      "notify its Observer after creation" in {
        controller.newGame()
        observer.updated should be(true)
        controller.playfield should be(new Playfield)
      }
      "notify its Observer after setting fieldsize" in {
        controller.createGrid(3)
        observer.updated should be(true)
        controller.playfield should be(g1)
      }
      "notfiy its Oberver after setting playername" in {
        controller.addPlayer("Test")
        controller.changeGameState(0)
        controller.addPlayer("")
        controller.changeGameState(1)
        observer.updated should be(true)
        controller.playfield should be(g2)
      }
      "notify its Observer after firstCard" in {
        controller.firstCard(14)
        observer.updated should be(true)
        val fresh = RawCardFactory("selectCard", 0, 14).drawCard()
        controller.playfield.freshCard should be(fresh)
      }
      "notify its Observer after setting a cell" in {
        controller.placeCard(1,1)
        observer.updated should be(true)
        val fresh = RawCardFactory("selectCard", 0, 14).drawCard().finalCard(1, 1)
        controller.playfield.grid.card(1,1) should be(fresh)
      }
      "notif its Observer after undo/redo" in {
        val oldplayfield = controller.playfield
        controller.undo()
        observer.updated should be(true)
        controller.playfield should be(g3)

        controller.redo()
        observer.updated should be(true)
        controller.playfield should be(oldplayfield)
      }
      "get GameStat" in {
        controller.getGameState should be(3)
      }
      "notifiy its Observer after rotation" in {
        controller.firstCard(14)
        controller.rotateLeft()
        observer.updated should be(true)
        val fresh = RawCardFactory("selectCard", 0, 14).drawCard()
        controller.playfield.freshCard should be(fresh.rotateLeft)
        controller.rotateRight()
        observer.updated should be(true)
        controller.playfield.freshCard should be(fresh)
      }
      "notify its Observer after select Area" in {
        val fresh = RawCardFactory("selectCard", 0, 14).drawCard().setPlayerToArea(0)
        controller.selectArea(0)
        observer.updated should be(true)
        controller.playfield.freshCard should be(fresh)
      }
    }
  }
}

 */
