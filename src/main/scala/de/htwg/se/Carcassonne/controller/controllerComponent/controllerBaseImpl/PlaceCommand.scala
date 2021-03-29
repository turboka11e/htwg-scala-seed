package de.htwg.se.Carcassonne.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.Carcassonne.model.playfieldComponent.{CardManipulatorInterface, PlayfieldInterface}
import de.htwg.se.Carcassonne.util.Command

class PlaceCommand(x:Int, y:Int, oldPlayfield:PlayfieldInterface, controller: Controller) extends Command {

  var oldFreshCard:CardManipulatorInterface = _

  override def doStep():Unit = {
    controller.playfield = controller.playfield.placeCard(x, y)
    if(controller.playfield.success) {
      controller.playfield = controller.playfield.nextPlayer.getFreshCard
    }
    oldFreshCard = controller.playfield.freshCard
  }

  override def undoStep(): Unit = {
    controller.playfield = oldPlayfield
  }

  override def redoStep(): Unit = {
    controller.playfield = controller.playfield.placeCard(x, y)
    if(controller.playfield.success) {
      controller.playfield = controller.playfield.nextPlayer
      controller.playfield = controller.playfield.setCurrentFreshCard(oldFreshCard)
    }
  }

}
