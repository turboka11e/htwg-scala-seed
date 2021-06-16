package de.htwg.se.Carcassonne.controller.controllerComponent

import de.htwg.se.Carcassonne.model.playfieldComponent.playfieldBaseImpl.{CardManipulator, Playfield}
import de.htwg.se.Carcassonne.util.Command

class PlaceCommand(x:Int, y:Int, oldPlayfield:Playfield, controller: Controller) extends Command {

  var oldFreshCard:CardManipulator = _

  override def doStep():Unit = {
    controller.playfield = controller.playfield.placeCard(x, y)
    if(controller.playfield.getSuccess) {
      controller.playfield = controller.playfield.nextPlayer.getFreshCard
    }
    oldFreshCard = controller.playfield.freshCard
  }

  override def undoStep(): Unit = {
    controller.playfield = oldPlayfield
  }

  override def redoStep(): Unit = {
    controller.playfield = controller.playfield.placeCard(x, y)
    if(controller.playfield.getSuccess) {
      controller.playfield = controller.playfield.nextPlayer
      controller.playfield = controller.playfield.copy(freshCard = oldFreshCard)
    }
  }

}
