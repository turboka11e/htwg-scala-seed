package de.htwg.se.Carcassonne.model

import scala.util.Random

case class CardCreator(playerCard: Int, card: Card = new Card((0, 0))){

  def this() = this(playerCard = -1)

  val randomCards: List[Card] = List(

    //Card(List(Area('r', List('w', 'e')), Area('c', List('n')), Area('g', List('s')))),

    //Card(List(Area('c', List('n')), Area('g', List('s', 'w', 'e')))),

    //Card(List(Area('c', List('w', 'e')), Area('g', List('s', 'n')))), falsch

    //Card(List(Area('c', List('w')), Area('c', List('e')), Area('g', List('n', 's')))),

    Card(List(Area('c', List('n', 'e')), Area('g', List('s', 'w')))),

    //Card(List(Area('c', List('n')), Area('g', List('w')), Area('r', List('s', 'e')))),

    Card(List(Area('c', List('n')), Area('c', List('w')), Area('c', List('e')), Area('c', List('s'))))

  )

  def randCard:CardCreator = {
    val r = Random
    copy(card = randomCards(r.nextInt(randomCards.size)))
  }

  def rotateRight:CardCreator = {
    copy(card = card.rotateRight())
  }

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
