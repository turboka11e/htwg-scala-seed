package de.htwg.se.Carcassonne.model

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class CardSpec extends WordSpec with Matchers {

  "A Cell" when {
    "not set to any value " should {
      val emptyCell = Card()
      "have value 0" in {
        emptyCell.nord should be(' ')
      }
      "not be set" in {
        emptyCell.isSet should be(false)
      }
    }
    "set to a specific value" should {
      val nonEmptyCell = Card(nord = 'c', west = 'r', east = 'g', south = 'c')
      "return that value" in {
        nonEmptyCell.nord should be('c')
      }
      "be set" in {
        nonEmptyCell.isSet should be(true)
      }
    }
  }

}