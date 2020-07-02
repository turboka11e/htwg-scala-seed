package de.htwg.se.Carcassonne.controller.controllerComponent.controllerBaseImpl

import com.google.inject.{Guice, Inject, Injector}
import net.codingwell.scalaguice.InjectorExtensions._
import de.htwg.se.Carcassonne.CarcassonneModule
import de.htwg.se.Carcassonne.controller.controllerComponent.ControllerInterface
import de.htwg.se.Carcassonne.model.playfieldComponent.PlayfieldInterface
import de.htwg.se.Carcassonne.util.UndoManager


class Controller @Inject() (var playfield: PlayfieldInterface) extends ControllerInterface {

  private val undoManager = new UndoManager
  val injector: Injector = Guice.createInjector(new CarcassonneModule)

  def newGame():Unit = {
    playfield = injector.instance[PlayfieldInterface]
    publish(new SetGrid)
  }

  def getPlayfield: PlayfieldInterface = playfield

  def createGrid(size: Int):Unit = {
    playfield = playfield.fieldSize(size)
    publish(new AddPlayers)
  }

  def addPlayer(name: String): Unit = {
    playfield.getGameState match {
      case 1 => playfield = playfield.addPlayer(name)
        publish(new AddPlayers)
      case _ =>
    }
  }

  def firstCard():Unit = {
    playfield = playfield.changeGameState(3).getFreshCard
    publish(new PlayfieldChanged)
  }

  def firstCard(select: Int):Unit = {
    playfield = playfield.changeGameState(3).getFreshCard(select)
    publish(new PlayfieldChanged)
  }

  def changeGameState(gs: Int):Unit = {
    playfield = playfield.changeGameState(gs)
  }

  def getGameState:Int = playfield.getGameState

  def rotateRight():Unit = {
    playfield = playfield.rotateR
    publish(new PlayfieldChanged)
  }

  def rotateLeft():Unit = {
    playfield = playfield.rotateL
    publish(new PlayfieldChanged)
  }

  def selectArea(nr: Int):Unit = {
    playfield = playfield.selectArea(nr)
    publish(new PlayfieldChanged)
  }

  def placeCard(x:Int, y:Int):Unit = {
    undoManager.doStep(new PlaceCommand(x, y, playfield, this))
    publish(new PlayfieldChanged)
  }

  def undo(): Unit = {
    undoManager.undoStep()
    publish(new PlayfieldChanged)
  }

  def redo(): Unit = {
    undoManager.redoStep()
    publish(new PlayfieldChanged)
  }


  def playFieldToString: String = playfield.playFieldToString

}
