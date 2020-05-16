package de.htwg.se.Carcassonne.model

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class GridSpec extends WordSpec with Matchers {
  "A Grid is the playingfield of Carcassonne. A Grid" when {
    "to be constructed" should {
      "be created with the length of its edges as size. Practically relevant are size 1, 3, 5, 7, and bigger" in {
        val tinygrid = new Grid(1)
        val smallGrid = new Grid(3)
        val normalGrid = new Grid(5)
        val biggerGrid = new Grid(7)
      }
      "for test purposes only created with a Matrix of Card" in {
        val awkwardGrid = Grid(new Matrix(2, Card()))
        val testGrid = Grid(Matrix[Card](Vector(Vector(Card(), Card()), Vector(Card(), Card()))))
      }
    }
    "created properly but empty" should {
      val tinygrid = new Grid(1)
      val smallGrid = new Grid(4)
      val normalGrid = new Grid(9)
      val awkwardGrid = new Grid(2)
      "give access to its Cells" in {
        tinygrid.card(0, 0) should be(Card())
        smallGrid.card(0, 0) should be(Card())
        smallGrid.card(0, 1) should be(Card())
        smallGrid.card(1, 0) should be(Card())
        smallGrid.card(1, 1) should be(Card())
      }
      "allow to set individual Cells and remain immutable" in {
        val changedGrid = smallGrid.set(0, 0, List(Area('c', List('n', 'w', 'e', 's'))))
        changedGrid.card(0, 0) should be(Card(List(Area('c', List('n', 'w', 'e', 's')))))
        smallGrid.card(0, 0) should be(Card())
      }
    }
  }
}
