package de.htwg.se.Carcassonne.controller

import de.htwg.se.Carcassonne.model.{Card, CardCreator, Grid, Playfield}
import de.htwg.se.Carcassonne.util.Command

class PlaceCommand(x:Int, y:Int, oldPlayfield:Playfield, controller: Controller) extends Command {

  override def doStep():Unit = {
    controller.playfield = controller.playfield.placeCard(x, y)
    if(controller.playfield.getSuccess) {
      controller.playfield = controller.playfield.nextPlayer.getFreshCard
    }
  }

  override def undoStep(): Unit = {
    controller.playfield = oldPlayfield
  }

  override def redoStep(): Unit = {
    doStep()
  }

}
