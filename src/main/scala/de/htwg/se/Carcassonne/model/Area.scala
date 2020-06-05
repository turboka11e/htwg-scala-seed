package de.htwg.se.Carcassonne.model

case class Area(value:Char = ' ', corners:List[Char] = List('n', 'w', 'e', 's'), player:Int = -1, xy:(Int, Int) = (-1, -1)) {

  def isEmpty:Boolean = if(xy.equals(-1, -1)) true else false

  def getValue:Char = value

  def getCorners:List[Char] = corners

  def getPlayer:Int = player

  def rotateRight():Area = {
    val rotatedcorners = corners.map {case 'w' => 'n'; case 'n' => 'e'; case 'e' => 's'; case 's' => 'w'}
    Area(getValue, rotatedcorners, player)
  }

}
