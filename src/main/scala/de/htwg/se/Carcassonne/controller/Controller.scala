package de.htwg.se.Carcassonne.controller

import de.htwg.se.Carcassonne.model.Playfield
import de.htwg.se.Carcassonne.util.Observable

class Controller(var playfield: Playfield) extends Observable {

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

  def decide(dc:Boolean):Unit = {
    playfield.gameState match {
      case 2 =>
        if(!dc){
          playfield = playfield.changeGameState(3)
          playfield = playfield.getFreshCard
        } else {
          playfield = playfield.changeGameState(1)
        }
      case 3 => if(dc) playfield = playfield.changeGameState(4)

      case 4 => if(!dc) playfield = playfield.changeGameState(5)

      case _ =>
    }
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
    playfield = playfield.placeCard(x, y)
    if(playfield.getSuccess) {
      playfield = playfield.nextPlayer.getFreshCard
    }
    notifyObservers
  }

  def playFieldToString: String = playfield.playFieldToString

}
