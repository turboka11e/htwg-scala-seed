package de.htwg.se.Carcassonne.model.gridComponent.gridBaseImpl

import de.htwg.se.Carcassonne.model.gridComponent.AreaInterface

case class Area(value:String, edges:List[String], player:Option[Int] = None, areaID:(Int, Int)) extends AreaInterface {

  def getValue:String = value

  def getCorners:List[String] = edges

  def getPlayer:Option[Int] = player

  def setPlayer(p:Option[Int]):AreaInterface = copy(player = p)

  def getCoord:(Int, Int) = areaID

  def rotateRight():AreaInterface = copy(edges = edges.map {case "w" => "n"; case "n" => "e"; case "e" => "s"; case "s" => "w"})

}
