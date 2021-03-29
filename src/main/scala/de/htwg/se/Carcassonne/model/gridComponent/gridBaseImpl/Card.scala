package de.htwg.se.Carcassonne.model.gridComponent.gridBaseImpl

import de.htwg.se.Carcassonne.model.gridComponent.{AreaInterface, CardInterface}

case class Card(areas: List[AreaInterface], private val id: (Int, Int) = (-1, 0)) extends CardInterface {

  def this(xy: (Int, Int)) = this(areas = List(Area(corners = List('n'), xy = (xy._1, xy._2)),
    Area(corners = List('w'), xy = (xy._1, xy._2)), Area(corners = List('e'), xy = (xy._1, xy._2)),
    Area(corners = List('s'), xy = (xy._1, xy._2))), id = (-1, 0))

  def isEmpty: Boolean = areas.head.getValue == ' ' && areas.size == 4

  /* id => CardNumber (-1 is EmptyCard) and Rotation of Card */
  def getID: (Int, Int) = id

  def setAreasXY(x: Int, y: Int): CardInterface = copy(areas = areas.map(areas => areas.setCoord(x, y)))

  def getValue(dir: Char): Char = areas.find(_.getCorners.contains(dir)).get.getValue

  def getPlayer(dir: Char): Int = areas.find(_.getCorners.contains(dir)).get.getPlayer

  def getAreas: List[AreaInterface] = areas

  def getCornersLookingFrom(dir: Char): List[Char] = areas.find(_.getCorners.contains(dir)).get.getCorners

  def getAreaLookingFrom(dir: Char): AreaInterface = areas.find(_.getCorners.contains(dir)).get

  def rotateRight(): CardInterface = {
    val rotatedAreas: List[AreaInterface] = areas.map { area => area.rotateRight() }
    val current_rotation = id._2
    if(current_rotation < 3) {
      Card(rotatedAreas, id = (id._1, current_rotation + 1))
    } else {
      Card(rotatedAreas, id = (id._1, 0))
    }
  }

  override def toString: String = {

    var tmpstring = ""

    val o = getValue('n')
    var ol = '┌'
    var or = '┐'
    if (getCornersLookingFrom('n').contains('w')) ol = o
    if (getCornersLookingFrom('n').contains('e')) or = o
    tmpstring += s" $ol $o $or"

    tmpstring += s"\n"

    val l = getValue('w')
    val r = getValue('e')
    var m = ' '
    if (getCornersLookingFrom('e').contains('w')) m = l
    if (getCornersLookingFrom('n').contains('s')) m = getValue('n')
    tmpstring += s" $l $m $r"

    tmpstring += "\n"

    val u = getValue('s')
    var ul = '└'
    var ur = '┘'
    if (getCornersLookingFrom('s').contains('w')) ul = u
    if (getCornersLookingFrom('s').contains('e')) ur = u
    tmpstring += s" $ul $u $ur"

    tmpstring += "\n"

    tmpstring

  }

  private val color = List(Console.BLUE, Console.RED, Console.YELLOW, Console.GREEN)

  // todo partial function

  def topString: String = {
    val o = color(areas.indexWhere(p => p == getAreaLookingFrom('n'))) + getValue('n') + Console.RESET
    var ol = "┌"
    var or = "┐"
    if (getCornersLookingFrom('n').contains('w')) ol = o
    if (getCornersLookingFrom('n').contains('e')) or = o
    s" $ol $o $or"
  }

  def midString: String = {
    val l = color(areas.indexWhere(p => p == getAreaLookingFrom('w'))) + getValue('w') + Console.RESET
    val r = color(areas.indexWhere(p => p == getAreaLookingFrom('e'))) + getValue('e') + Console.RESET
    var m = " "
    if (getCornersLookingFrom('e').contains('w')) m = l
    if (getCornersLookingFrom('n').contains('s')) m = color(areas.indexWhere(p => p == getAreaLookingFrom('n'))) + getValue('n') + Console.RESET
    s" $l $m $r"
  }

  def lowString: String = {
    val u = color(areas.indexWhere(p => p == getAreaLookingFrom('s'))) + getValue('s') + Console.RESET
    var ul = "└"
    var ur = "┘"
    if (getCornersLookingFrom('s').contains('w')) ul = u
    if (getCornersLookingFrom('s').contains('e')) ur = u
    s" $ul $u $ur"
  }
}
