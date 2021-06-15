package de.htwg.se.Carcassonne.model

import scala.util.Random

case class CardCreator(playerCard: Player, card: Card = Card()){

  val randomCards: List[Card] = List(

    Card(List(Area('r', List('w', 'e')), Area('c', List('n')), Area('g', List('s')))),

    Card(List(Area('c', List('n')), Area('g', List('s', 'w', 'e')))),

    Card(List(Area('c', List('w', 'e')), Area('g', List('s', 'n')))),

    Card(List(Area('c', List('w')), Area('c', List('e')), Area('g', List('n', 's')))),

    Card(List(Area('c', List('n', 'e')), Area('g', List('s', 'w')))),

    Card(List(Area('c', List('n')), Area('g', List('w')), Area('r', List('s', 'e'))))

  )

  def randCard:CardCreator = {
    val r = Random
    copy(card = randomCards(r.nextInt(randomCards.size)))
  }

  def rotateRight:CardCreator = {
    copy(card = card.rotateRight())
  }

  def rotateLeft:CardCreator = {
    copy(card = card.rotateRight().rotateRight().rotateRight())
  }

  def showCard:String = {
    card.toString
  }

  def showTerri:String = {
    var tmpString = ""
    for((x, i) <- card.areas.zipWithIndex){
      tmpString += s"Area: $i " + x.getValue + " " + x.getCorners.toString() + " " + x.getPlayer.name + "\n"
    }
    tmpString
  }

  def setPlayerToArea(index:Int):CardCreator = {
    if(index < card.areas.size){
      val replacedPlayer = card.areas.apply(index).copy(player = playerCard)
      val replacedCard = card.areas.updated(index, replacedPlayer)
      this.copy(card = Card(replacedCard))
    } else {
      copy()
    }
  }

  def finalCard:Card = this.card

}