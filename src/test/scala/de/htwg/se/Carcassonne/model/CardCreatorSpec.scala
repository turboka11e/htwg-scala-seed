package de.htwg.se.Carcassonne.model

import org.scalatest._

class CardCreatorSpec extends WordSpec with Matchers {
  "An CardCreator " when {
    "called " should {
      val r1 = CardCreator(1)
      "create a Card with " in {
      }
    }
    "pick a random card" should {
      val r = 5;
      val randomCard = CardCreator(1)
    }
  }
}
