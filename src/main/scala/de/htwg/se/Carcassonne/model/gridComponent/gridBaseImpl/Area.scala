package de.htwg.se.Carcassonne.model.gridComponent.gridBaseImpl

import de.htwg.se.Carcassonne.model.gridComponent.AreaInterface

case class Area(value: Char = ' ', corners: List[Char] = List('n', 'w', 'e', 's'), player: Int = -1, xy: (Int, Int) = (-1, -1)) extends AreaInterface {

  def isEmpty: Boolean = xy.equals(-1, -1)

  def setPlayer(p: Int): AreaInterface = copy(player = p)

  def setCoord(x: Int, y: Int): AreaInterface = copy(xy = (x, y))

  def rotateRight(): AreaInterface = {
    val rotatedcorners = corners.map { case 'w' => 'n'; case 'n' => 'e'; case 'e' => 's'; case 's' => 'w' }
    Area(value, rotatedcorners, player)
  }

}
