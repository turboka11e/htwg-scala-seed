package de.htwg.se.Carcassonne.controller.controllerComponent
import de.htwg.se.Carcassonne.controller.GameState

import scala.swing.Publisher

trait ControllerInterface extends Publisher {

  def newGame():Unit
  def createGrid(size: Int):Unit
  def addPlayer(name: String): Unit
  def firstCard():Unit
  def firstCard(select: Int):Unit
  def changeGameState(gs: GameState):Unit
  def getGameState:GameState
  def rotateRight():Unit
  def rotateLeft():Unit
  def selectArea(nr: Int):Unit
  def placeCard(x:Int, y:Int):Unit
  def undo(): Unit
  def redo(): Unit
  def playFieldToString: String

}
