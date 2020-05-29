package de.htwg.se.Carcassonne.controller

import de.htwg.se.Carcassonne.model.Playfield
import de.htwg.se.Carcassonne.util.Observable

class Controller(var playfield: Playfield) extends Observable {

  def newGame():Unit = {
    playfield = Playfield()
    notifyObservers
  }

  def forkDigit(singleDigit:Int):Unit = {
    if(playfield.getGameState == 0){
      createGrid(singleDigit)
    }
  }

  def createGrid(size: Int):Unit = {
    playfield = playfield.fieldSize(size)
    notifyObservers
  }

  def addPlayer(name:String):Unit = {
    playfield = playfield.addPlayer(name)
    notifyObservers
  }

  def decide(dc:Boolean):Unit = {
    playfield.gameState match {
      case 2 =>
        if(!dc){
          playfield = playfield.changeGameState(3)
          playfield = playfield.getFreshCard
        }
      case 3 =>
        if(dc){
          playfield = playfield.changeGameState(4)
        }
    }
    notifyObservers
  }

  def rotateRight():Unit = {
    playfield = playfield.rotateR
    notifyObservers
  }

  def rotateLeft():Unit = {
    playfield = playfield.rotateL
    notifyObservers
  }

  def playFieldToString: String = playfield.playFieldToString

}
