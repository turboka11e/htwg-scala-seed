package de.htwg.se.Carcassonne.controller.controllerComponent

import de.htwg.se.Carcassonne.model.playfieldComponent.PlayfieldInterface

import scala.swing.Publisher

trait ControllerInterface extends Publisher {

  def statusText:String
  def newGame():Unit
  def playfield: PlayfieldInterface
  def createGrid(size: Int):Unit
  def addPlayer(name: String): Unit
  def firstCard():Unit
  def firstCard(select: Int):Unit
  def changeGameState(gs: Int):Unit
  def getGameState:Int
  def rotateRight():Unit
  def rotateLeft():Unit
  def selectArea(nr: Int):Unit
  def placeCard(x:Int, y:Int):Unit
  def placeAble():Unit
  def undo(): Unit
  def redo(): Unit
  def save(): Unit
  def load(): Unit
  def getScore(): String
}
