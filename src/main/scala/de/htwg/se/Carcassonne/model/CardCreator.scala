package de.htwg.se.Carcassonne.model

import scala.util.Random

case class CardCreator(playerCard: Int, card: Card = new Card((0, 0))){

  def this() = this(playerCard = -1)

  val randomCards: List[Card] = List(

    Card(List(Area('r', List('w', 'e')), Area('c', List('n')), Area('g', List('s')))),       // D

    Card(List(Area('c', List('n')), Area('g', List('s', 'w', 'e')))),                        // E

    Card(List(Area('c', List('w', 'e')), Area('g', List('s')), Area('g', List('n')))),       // F

    Card(List(Area('c', List('w')), Area('c', List('e')), Area('g', List('n', 's')))),       // H

    Card(List(Area('c', List('n', 'e')), Area('g', List('s', 'w')))),                        // I

    Card(List(Area('c', List('n')), Area('g', List('w')), Area('r', List('s', 'e')))),       // J

    Card(List(Area('c', List('n')), Area('g', List('e')), Area('r', List('s', 'w')))),       // K

    Card(List(Area('c', List('n')), Area('r', List('s', 'w', 'e')))),                        // L

    Card(List(Area('c', List('n', 'w')), Area('g', List('s', 'e')))),                        // M

    Card(List(Area('c', List('n', 'w')), Area('r', List('s', 'e')))),                        // O

    Card(List(Area('c', List('n', 'w', 'e')), Area('g', List('s')))),                        // R

    Card(List(Area('c', List('n', 'w', 'e')), Area('r', List('s')))),                        // T

    Card(List(Area('r', List('n', 's')), Area('g', List('e')), Area('g', List('w')))),      // U

    Card(List(Area('r', List('w', 's')), Area('g', List('n', 'e'))))                        // V

    //Card(List(Area('c', List('n')), Area('c', List('w')), Area('c', List('e')), Area('c', List('s'))))  // all c

  )

  def randCard(select:Int = Random.nextInt(randomCards.size)):CardCreator = copy(card = randomCards(select))

  def rotateRight:CardCreator = copy(card = card.rotateRight())

  def rotateLeft:CardCreator = copy(card = card.rotateRight().rotateRight().rotateRight())

  def setPlayerToArea(index:Int):CardCreator = {
    if(index < card.areas.size){
      val replacedPlayer = card.areas.apply(index).copy(player = playerCard)
      val replacedCard = card.areas.updated(index, replacedPlayer)
      this.copy(card = Card(replacedCard))
    } else {
      copy()
    }
  }

  def finalCard(x:Int, y:Int):Card = this.card.setAreasXY(x, y)

}
