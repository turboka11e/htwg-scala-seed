package de.htwg.se.Carcassonne.model

case class Area(value:Char = ' ', corners:List[Char]) {

  def getValue:Char = value

  def getCorners:List[Char] = corners
}
