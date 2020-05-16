package de.htwg.se.Carcassonne.model

case class Area(value:Char = ' ', corners:List[Char] = List('n', 'w', 'e', 's')) {

  def getValue:Char = value

  def getCorners:List[Char] = corners

  def rotateRight():Area = {
    val rotatedcorners = corners.map {case 'w' => 'n'; case 'n' => 'e'; case 'e' => 's'; case 's' => 'w'}
    Area(getValue, rotatedcorners)
  }

}
