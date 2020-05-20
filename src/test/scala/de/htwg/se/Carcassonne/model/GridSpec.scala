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
      val printGrid = new Grid(3)
      val awkwardGrid = new Grid(2)
      "give access to its Cells" in {
        tinygrid.card(0, 0) should be(Card())
        smallGrid.card(0, 0) should be(Card())
        smallGrid.card(0, 1) should be(Card())
        smallGrid.card(1, 0) should be(Card())
        smallGrid.card(1, 1) should be(Card())
      }
      "allow to set individual Cells and remain immutable" in {
        val changedGrid = smallGrid.set(0, 0, Card(List(Area('c', List('n', 'w', 'e', 's')))))
        changedGrid.card(0, 0) should be(Card(List(Area('c', List('n', 'w', 'e', 's')))))
        smallGrid.card(0, 0) should be(Card())
      }
      "print field as String correctly" in {
        printGrid.toString should be(" ┌   ┐ ┌   ┐ ┌   ┐\n " +
                                      "                 " +
                                   "\n └   ┘ └   ┘ └   ┘\n " +
                                      "┌   ┐ ┌   ┐ ┌   ┐\n" +
                                      "                  \n " +
                                      "└   ┘ └   ┘ └   ┘\n " +
                                      "┌   ┐ ┌   ┐ ┌   ┐\n" +
                                      "                  \n " +
                                      "└   ┘ └   ┘ └   ┘\n")
      }
    }
    "created properly and with one Card" should {
      val oneCard = Card(List(Area('r', List('w', 'e')), Area('c', List('n')), Area('g', List('s'))))

      val validNordCard = Card(List(Area('c', List('n', 's', 'w')), Area('g', List('e'))))
      val notValidNordCard = Card(List(Area('r', List('s', 'w')), Area('g', List('n', 'e'))))

      val validSouthCard = Card(List(Area('g', List('n', 's', 'w')), Area('c', List('e'))))
      val notValidSouthCard = Card(List(Area('r', List('s', 'w')), Area('r', List('n', 'e'))))

      val validWestCard = Card(List(Area('c', List('n', 's', 'w')), Area('r', List('e'))))
      val notValidWestCard = Card(List(Area('r', List('s', 'w')), Area('g', List('n', 'e'))))

      val validEastCard = Card(List(Area('r', List('n', 's', 'w')), Area('g', List('e'))))
      val notValidEastCard = Card(List(Area('c', List('s', 'w')), Area('g', List('n', 'e'))))

      val emptyGrid = new Grid(5)
      val gridWithOneCard = emptyGrid.set(2, 2, oneCard)
      "only allow placing same territories next to each other" in {
        gridWithOneCard.checkSet(2, 1, validNordCard) should be(true)
        gridWithOneCard.checkSet(2, 1, notValidNordCard) should be(false)

        gridWithOneCard.checkSet(2, 3, validSouthCard) should be(true)
        gridWithOneCard.checkSet(2, 3, notValidSouthCard) should be(false)

        gridWithOneCard.checkSet(1, 2, validWestCard) should be(true)
        gridWithOneCard.checkSet(1, 2, notValidWestCard) should be(false)

        gridWithOneCard.checkSet(3, 2, validEastCard) should be(true)
        gridWithOneCard.checkSet(3, 2, notValidEastCard) should be(false)
      }
      "only allow placing same territores next to each other and ignore Borders of the Game" in {
        val gridWithTwoCards = gridWithOneCard.set(0, 1, oneCard)

        gridWithTwoCards.checkSet(0, 0, validNordCard) should be(true)
      }
      "a new Card only be placed next to a Card" in {
        val gridWithTwoCards = gridWithOneCard.set(4, 3, oneCard)

        gridWithOneCard.checkSet(4, 4, oneCard) should be(false)
        gridWithTwoCards.checkSet(4, 4, validSouthCard) should be(true)

      }
    }
  }
}
