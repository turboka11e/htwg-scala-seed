package de.htwg.se.Carcassonne.model

import org.scalatest._

class TerritoriesSpec extends WordSpec with Matchers {

  "Territories grow in grid " when {
    "Cards are placed next to another " should {
      val emptyGrid = new Grid(3)
      val oneCard = Card(List(Area('r', List('w', 'e'), Player("Road1")), Area('c', List('n')), Area('g', List('s'))))
      val twoCard = Card(List(Area('r', List('w', 'e'), Player("Road2")), Area('c', List('n')), Area('g', List('s'))))
      val threeCard = Card(List(Area('r', List('w', 'e'), Player("Road3")), Area('c', List('n')), Area('c', List('s'))))
      val oneCardGrid = emptyGrid.place(1, 1, oneCard)
      val twoCardGrid = oneCardGrid.place(0, 1, twoCard)
      val threeCardGrid = twoCardGrid.place(1, 0, threeCard)
      "and create Territory List" in {
        oneCardGrid.getTerritories should be(List(List(Area('r', List('w', 'e'),
          Player("Road1"))), List(Area('g', List('s'), Player("not selected"))), List(Area('c', List('n'), Player("not selected")))))

        oneCardGrid.summonTerritories.toString should be("r 1 Road1\ng 1 not selected\nc 1 not selected\n")

        twoCardGrid.getTerritories should be(List(List(Area('r', List('w', 'e'), Player("Road2")), Area('r', List('w', 'e'),
          Player("Road1"))), List(Area('g', List('s'), Player("not selected"))), List(Area('c', List('n'), Player("not selected"))),
          List(Area('g', List('s'), Player("not selected"))), List(Area('c', List('n'), Player("not selected")))))

        threeCardGrid.getTerritories should be(List(List(Area('r',List('w', 'e'), Player("Road3"))), List(Area('c',List('s'), Player("not selected")),
          Area('c',List('n'), Player("not selected"))), List(Area('c',List('n'), Player("not selected"))), List(Area('r',List('w', 'e'), Player("Road2")),
          Area('r',List('w', 'e'), Player("Road1"))), List(Area('g',List('s'), Player("not selected"))), List(Area('c',List('n'), Player("not selected"))),
          List(Area('g',List('s'), Player("not selected")))))

      }
    }
  }
}
