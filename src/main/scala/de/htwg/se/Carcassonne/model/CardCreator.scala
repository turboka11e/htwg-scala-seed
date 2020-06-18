package de.htwg.se.Carcassonne.model

case class CardCreator(playerCard: Int, card: Card = new Card((0, 0))){

  def this() = this(playerCard = -1)

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
