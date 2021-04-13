package de.htwg.se.Carcassonne.model.playfieldComponent.playfieldBaseImpl

import de.htwg.se.Carcassonne.model.gridComponent.CardInterface
import de.htwg.se.Carcassonne.model.gridComponent.gridBaseImpl.Card
import de.htwg.se.Carcassonne.model.playfieldComponent.CardManipulatorInterface

case class CardManipulator(playerCard: Int, card: CardInterface = new Card((0, 0))) extends CardManipulatorInterface {

  def this() = this(playerCard = -1)

  def rotateRight:CardManipulator = copy(card = card.rotateRight())

  def rotateLeft:CardManipulator = copy(card = card.rotateRight().rotateRight().rotateRight())

  def setPlayerToArea(index:Int):CardManipulator = {
    if(index < card.areas.size){
      val replacedPlayer = card.areas.apply(index).setPlayer(playerCard)
      val replacedCard = card.areas.updated(index, replacedPlayer)
      this.copy(card = Card(replacedCard, card.id))
    } else {
      this
    }
  }

  def finalCard(x:Int, y:Int):CardInterface = this.card.setAreasXY(x, y)

}
