package de.htwg.se.Carcassonne.model.gridComponent.gridBaseImpl

import de.htwg.se.Carcassonne.model.gridComponent.CardInterface
import de.htwg.se.Carcassonne.model.playfieldComponent.CardManipulatorInterface

case class CardManipulator(player: Int, card: CardInterface) extends CardManipulatorInterface {

  var rotations = 0
  var select:Option[Int] = None

  def getCard:CardInterface = card

  def rotateRight:CardManipulator = copy(card = card.rotateRight())

  def rotateLeft:CardManipulator = copy(card = card.rotateRight().rotateRight().rotateRight())

  def setPlayerToArea(index:Int):CardManipulator = {
    if(index < card.getAreas().size){
      val replacedPlayer = card.getAreas().get.apply(index).setPlayer(Option(player))
      val replacedCard = card.getAreas().get.updated(index, replacedPlayer)
      this.copy(card = Card(Option(replacedCard), card.getIDRot))
    } else {
      this
    }
  }

  def drawTemplate(x:Int, y:Int):(String, Int, (Int, Int), Option[(Int, Int)]) =
    (card.getIDRot.get._1, card.getIDRot.get._2, (x, y),

}
