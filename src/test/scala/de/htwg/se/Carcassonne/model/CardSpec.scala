package de.htwg.se.Carcassonne.model

import org.scalatest._

class CardSpec extends WordSpec with Matchers {

  "A Card" when {
    "not set to any value " should {
      val emptyCell = Card()
      "have List with ' '" in {
        emptyCell.areas should be(List(Area(corners = List('n', 'w', 'e', 's'), player = -1, xy = (-1, -1))))
      }
      "not be set" in {
        emptyCell.isEmpty should be(false)
      }
    }
    "set to a specific value" should {
      val nonEmptyCell = Card(List(Area('c', List('n', 's')), Area('g', List('w', 'e'))))
      "return that value" in {
        nonEmptyCell.getValue('n') should be('c')
        nonEmptyCell.getValue('w') should be('g')
        nonEmptyCell.getValue('e') should be('g')
        nonEmptyCell.getValue('s') should be('c')
      }
      "be set" in {
        nonEmptyCell.isEmpty should be(false)
      }
    }



    "set to a specific value" should {
      val nonEmptyCell = Card(List(Area('c', List('n', 's'), player = 0), Area('g', List('w', 'e'), player = 1)))
      "change areas coordination" in {
        nonEmptyCell.setAreasXY(1, 1) should be(Card(List(Area('c', List('n', 's'), xy = (1, 1), player = 0),
          Area('g', List('w', 'e'), xy = (1, 1), player = 1))))
      }
      "get Value and Player from specific direction" in {
        nonEmptyCell.getValue('n') should be('c')
        nonEmptyCell.getValue('e') should be('g')

        nonEmptyCell.getPlayer('n') should be(0)
        nonEmptyCell.getPlayer('e') should be(1)
      }
    }
    "looking from a corner" should {
      val nonEmptyCell = Card(List(Area('c', List('n', 's')), Area('g', List('w', 'e'))))
      "return List of all corners area touches" in {
        nonEmptyCell.getCornersLookingFrom('n') should be(List('n', 's'))
      }
    }
    "looking from a corner" should {
      val nonEmptyCell = Card(List(Area('c', List('n', 's')), Area('g', List('w', 'e'))))
      "return Area that touches that corner" in {
        nonEmptyCell.getAreaLookingFrom('n') should be(Area('c', List('n', 's')))
      }
    }
    "containing areas" should{
      val validAreaCard = Card(List(Area('c', List('n', 's')), Area('g', List('w', 'e'))))
      val nonValidAreaCard = Card(List(Area('c', List('n', 's')), Area('g', List('n', 'e'))))
      "be valid" in {
        validAreaCard.isValid should be(true)
      }
      "valid containing areas could rotate" in{
        validAreaCard.rotateRight() should be (Card(List(Area('c', List('e' , 'w')), Area('g', List('n', 's')))))
      }
      "not be valid" in {
        nonValidAreaCard.isValid should be(false)
      }
      "print out nicely" in {
        val c = Console.BLUE + "c" + Console.RESET
        val g = Console.RED + "g" + Console.RESET
        validAreaCard.toString should be(" ┌ c ┐\n g c g\n └ c ┘\n")
        validAreaCard.topString should be(s" ┌ $c ┐")
        validAreaCard.midString should be(s" " + g + " " + c + " " + g)
        validAreaCard.lowString should be(s" └ $c ┘")
      }
    }
  }

}