package de.htwg.se.Carcassonne.model

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class CardSpec extends WordSpec with Matchers {

  "A Card" when {
    "not set to any value " should {
      val emptyCell = Card()
      "have List with ' '" in {
        emptyCell.areas should be(List(Area(corners = List('n')), Area(corners = List('w')), Area(corners = List('e')), Area(corners = List('s'))))
      }
      "not be set" in {
        emptyCell.isEmpty should be(true)
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
      "not be valid" in {
        nonValidAreaCard.isValid should be(false)
      }
    }
  }

}