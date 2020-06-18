package de.htwg.se.Carcassonne.model

import org.scalatest._

class CardManipulatorSpec extends WordSpec with Matchers {
  "An CardCreator " when {
    "called " should {
      val f1 = CardManipulator(0)
      "create a Card with " in {
      f1 should be(CardManipulator(playerCard = 0, card = new Card(0, 0)))
      }
    }
    "called for a fresh Card" should {
      val r1 = RawCardFactory("selectCard", 0, 0).drawCard()
      val randomCard = Card(List(Area('r', List('w', 'e')), Area('c', List('n')), Area('g', List('s'))))
      val setCard = Card(List(Area('r', List('w', 'e'), player = 0), Area('c', List('n')), Area('g', List('s'))))
      val finalCard = Card(List(Area('r', List('w', 'e'), xy = (1, 1)), Area('c', List('n'), xy = (1, 1)), Area('g', List('s'), xy = (1, 1))))
      "pick a random card" in {
        RawCardFactory("selectCard", 0, 0).drawCard() should be(RawCardFactory("selectCard", 0, 0).drawCard())
      }
      "rotate fresh Card" in {
        val r2 = r1
        r2.rotateRight should be(CardManipulator(playerCard = 0, card = randomCard.rotateRight()))
        r2.rotateLeft should be(CardManipulator(playerCard = 0, card = randomCard.rotateRight().rotateRight().rotateRight()))
      }
      "set Player to Areas" in {
        val freshCard = r1
        freshCard.setPlayerToArea(0) should be(CardManipulator(playerCard = 0, card = setCard))
      }
      "return finished adjusted FreshCard" in {
        val finishedFreshCard = r1
        finishedFreshCard.finalCard(1, 1) should be(finalCard)
      }
    }
  }
}
