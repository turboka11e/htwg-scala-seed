package de.htwg.se.Carcassonne.model

import org.scalatest._

class CardCreatorSpec extends WordSpec with Matchers {
  "An CardCreator " when {
    "called " should {
      val f1 = CardCreator(0)
      "create a Card with " in {
      f1 should be(CardCreator(playerCard = 0, card = new Card(0, 0)))
      }
    }
    "called for a fresh Card" should {
      val r1 = CardCreator(0)
      val randomCard = Card(List(Area('r', List('w', 'e')), Area('c', List('n')), Area('g', List('s'))))
      val setCard = Card(List(Area('r', List('w', 'e'), player = 0), Area('c', List('n')), Area('g', List('s'))))
      val finalCard = Card(List(Area('r', List('w', 'e'), xy = (1, 1)), Area('c', List('n'), xy = (1, 1)), Area('g', List('s'), xy = (1, 1))))
      "pick a random card" in {
        r1.randCard(0) should be(CardCreator(playerCard = 0, card = randomCard))
      }
      "rotate fresh Card" in {
        val r2 = r1.randCard(0)
        r2.rotateRight should be(CardCreator(playerCard = 0, card = randomCard.rotateRight()))
        r2.rotateLeft should be(CardCreator(playerCard = 0, card = randomCard.rotateRight().rotateRight().rotateRight()))
      }
      "set Player to Areas" in {
        val freshCard = r1.randCard(0)
        freshCard.setPlayerToArea(0) should be(CardCreator(playerCard = 0, card = setCard))
      }
      "return finished adjusted FreshCard" in {
        val finishedFreshCard = r1.randCard(0)
        finishedFreshCard.finalCard(1, 1) should be(finalCard)
      }
    }
  }
}
