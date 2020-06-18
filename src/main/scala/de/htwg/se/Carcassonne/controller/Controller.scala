package de.htwg.se.Carcassonne.controller

import de.htwg.se.Carcassonne.model.Playfield
import de.htwg.se.Carcassonne.util.{Observable, UndoManager}

class Controller(var playfield: Playfield) extends Observable {

  private val undoManager = new UndoManager

  def newGame():Unit = {
    playfield = Playfield()
    notifyObservers
  }

  def createGrid(size: Int):Unit = {
    playfield = playfield.fieldSize(size)
    notifyObservers
  }

  def addPlayer(name: String): Unit = {
    playfield.getGameState match {
      case 1 => playfield = playfield.addPlayer(name)
        notifyObservers
      case _ =>
    }
  }

  def firstCard():Unit = {
    playfield = playfield.changeGameState(3)
    playfield = playfield.getFreshCard
    notifyObservers
  }

  def changeGameState(gs: Int):Unit = {
    playfield = playfield.changeGameState(gs)
    notifyObservers
  }

  def getGameState:Int = playfield.getGameState

  def rotateRight():Unit = {
    playfield = playfield.rotateR
    notifyObservers
  }

  def rotateLeft():Unit = {
    playfield = playfield.rotateL
    notifyObservers
  }

  def selectArea(nr: Int):Unit = {
    playfield = playfield.selectArea(nr)
    notifyObservers
  }

  def placeCard(x:Int, y:Int):Unit = {
    undoManager.doStep(new PlaceCommand(x, y, playfield, this))
    notifyObservers
  }

  def undo(): Unit = {
    undoManager.undoStep()
    notifyObservers
  }

  def redo(): Unit = {
    undoManager.redoStep()
    notifyObservers
  }


  def playFieldToString: String = playfield.playFieldToString

}
