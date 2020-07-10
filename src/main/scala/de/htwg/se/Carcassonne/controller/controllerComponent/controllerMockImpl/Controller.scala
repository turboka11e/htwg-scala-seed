package de.htwg.se.Carcassonne.controller.controllerComponent.controllerMockImpl

import com.google.inject.Inject
import de.htwg.se.Carcassonne.controller.controllerComponent.ControllerInterface
import de.htwg.se.Carcassonne.model.playfieldComponent.PlayfieldInterface

class Controller @Inject() (var playfield: PlayfieldInterface) extends ControllerInterface {

  def statusText:String = ""

  def newGame():Unit = {}

  def getPlayfield: PlayfieldInterface = playfield

  def createGrid(size: Int):Unit = {}

  def addPlayer(name: String): Unit = {}

  def firstCard():Unit = {}

  def firstCard(select: Int):Unit = {}

  def changeGameState(gs: Int):Unit = {}

  def getGameState:Int = 1

  def rotateRight():Unit = {}

  def rotateLeft():Unit = {}

  def selectArea(nr: Int):Unit = {}

  def placeAble():Unit = {}

  def placeCard(x:Int, y:Int):Unit = {}

  def undo(): Unit = {}

  def redo(): Unit = {}

  def save(): Unit = {}

  def load(): Unit = {}

  def playFieldToString: String = ""

}