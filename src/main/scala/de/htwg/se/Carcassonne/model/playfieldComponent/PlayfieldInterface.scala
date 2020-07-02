package de.htwg.se.Carcassonne.model.playfieldComponent

trait PlayfieldInterface {
  def changeGameState(gs:Int):PlayfieldInterface
  def getGameState:Int
  def getSuccess:Boolean
  def fieldSize(size:Int):PlayfieldInterface
  def addPlayer(name:String):PlayfieldInterface
  def getFreshCard:PlayfieldInterface
  def getFreshCard(select:Int):PlayfieldInterface
  def rotateR:PlayfieldInterface
  def rotateL:PlayfieldInterface
  def selectArea(nr:Int):PlayfieldInterface
  def placeCard(x: Int, y: Int): PlayfieldInterface
  def nextPlayer: PlayfieldInterface
  def playFieldToString:String
}
