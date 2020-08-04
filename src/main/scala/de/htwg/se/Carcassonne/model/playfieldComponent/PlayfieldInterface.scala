package de.htwg.se.Carcassonne.model.playfieldComponent

import de.htwg.se.Carcassonne.model.gridComponent.{CardInterface, GridInterface}
import de.htwg.se.Carcassonne.model.playerComponent.Player

trait PlayfieldInterface {
  def changeGameState(gs:Int):PlayfieldInterface
  def getGameState:Int
  def getIsOn:Int
  def getGrid:GridInterface
  def getPlayers:List[Player]
  def getSuccess:Boolean
  def getCurrentFreshCard:CardManipulatorInterface
  def setCurrentFreshCard(f:CardManipulatorInterface):PlayfieldInterface
  def fieldSize(size:Int):PlayfieldInterface
  def addPlayer(name:String):PlayfieldInterface
  def getFreshCard:PlayfieldInterface
  def getFreshCard(select:Int):PlayfieldInterface
  def rotateR:PlayfieldInterface
  def rotateL:PlayfieldInterface
  def selectArea(nr:Int):PlayfieldInterface
  def placeable:Boolean
  def legalPlace(x: Int, y: Int):Boolean
  def placeCard(x: Int, y: Int): PlayfieldInterface
  def nextPlayer: PlayfieldInterface
//  def playFieldToString:String
}

trait CardManipulatorInterface {

  def getCard:CardInterface
  def rotateRight:CardManipulatorInterface
  def rotateLeft:CardManipulatorInterface
  def setPlayerToArea(index:Int):CardManipulatorInterface
  def finalCard(x:Int, y:Int):CardInterface

}

