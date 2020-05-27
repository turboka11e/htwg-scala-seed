package de.htwg.se.Carcassonne.model

import org.scalatest._

class TerritoriesSpec extends WordSpec with Matchers {

  "Territories " when {
    "Grid is empty " should {
      val emptyArea = Territories()
      val oneCard = Card(List(Area('r', List('w', 'e')), Area('c', List('n')), Area('g', List('s'))))
      val emptyGrid = new Grid(3)
      val oneCardGrid = emptyGrid.set(1, 1, oneCard)
      val twoCardGrid = oneCardGrid.set(0, 1, oneCard)
      val notEmptyTerri = emptyArea.addCard(oneCardGrid, 0, 1, oneCard)
      "have List with areas" in {
        emptyArea should be(Territories(List()))
      }
      "and one Card is added" in {
        emptyArea.addCard(emptyGrid, 1, 1, oneCard) should be(Territories(List(List(Area('r', List('w', 'e'),
          Player("not selected"))), List(Area('g', List('s'), Player("not selected"))), List(Area('c', List('n'), Player("not selected"))))))
        emptyArea.addCard(emptyGrid, 2, 2, oneCard) should be(Territories(List(List(Area('r', List('w', 'e'),
          Player("not selected"))), List(Area('g', List('s'), Player("not selected"))), List(Area('c', List('n'), Player("not selected"))))))
      }
      "and one Card is added next to another" in {
        emptyArea.addCard(oneCardGrid, 0, 1, oneCard) should be(Territories(List(List(Area('r', List('w', 'e'),
          Player("not selected")), Area('r', List('w', 'e'),
          Player("not selected"))), List(Area('g', List('s'), Player("not selected"))), List(Area('c', List('n'), Player("not selected"))))))
      }
      "and one Card is added again to another" in {
        notEmptyTerri.addCard(twoCardGrid, 2, 1, oneCard) should be(Territories(List(List(Area('r', List('w', 'e'),
          Player("not selected")), Area('r', List('w', 'e'),
          Player("not selected")), Area('r', List('w', 'e'),
          Player("not selected"))), List(Area('g', List('s'), Player("not selected"))), List(Area('c', List('n'), Player("not selected"))))))
      }
    }
    "Grid is filled " should {
      val emptyGrid = new Grid(3)

    }
  }
}
