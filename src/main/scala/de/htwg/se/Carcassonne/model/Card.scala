package de.htwg.se.Carcassonne.model

case class Card(areas:List[Area] = List(Area(corners = List('n')), Area(corners = List('w')), Area(corners = List('e')), Area(corners = List('s')))){

  def isEmpty: Boolean = areas.head.getValue == ' ' && areas.size == 4

  def getValue(dir:Char): Char = areas.find(_.getCorners.contains(dir)).get.getValue

  def getPlayer(dir:Char): Player = areas.find(_.getCorners.contains(dir)).get.getPlayer

  def getCornersLookingFrom(dir:Char): List[Char] = areas.find(_.getCorners.contains(dir)).get.getCorners

  def getAreaLookingFrom(dir:Char): Area = areas.find(_.getCorners.contains(dir)).get

  def isValid: Boolean = {
    var check = true
    for(x <- areas)
      for(y <- areas)
        for(c <- x.getCorners){
          if(y.getCorners.contains(c)  && y != x){
            check = false
          }
        }
    check
  }

  def rotateRight():Card = {
    val rotatedAreas:List[Area] = areas.map { x => x.rotateRight() }
    Card(rotatedAreas)
  }

  override def toString:String = {

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

  def topString:String = {
    val o = color(areas.indexWhere(p => p == getAreaLookingFrom('n')) ) + getValue('n') + Console.RESET
    var ol = "┌"
    var or = "┐"
    if (getCornersLookingFrom('n').contains('w')) ol = o
    if (getCornersLookingFrom('n').contains('e')) or = o
    s" $ol $o $or"
  }

  def midString:String = {
    val l = color(areas.indexWhere(p => p == getAreaLookingFrom('w')) ) + getValue('w') + Console.RESET
    val r = color(areas.indexWhere(p => p == getAreaLookingFrom('e')) ) + getValue('e') + Console.RESET
    var m = " "
    if (getCornersLookingFrom('e').contains('w')) m = l
    if (getCornersLookingFrom('n').contains('s')) m = color(areas.indexWhere(p => p == getAreaLookingFrom('n')) ) + getValue('n') + Console.RESET
    s" $l $m $r"
  }

  def lowString:String = {
    val u = color(areas.indexWhere(p => p == getAreaLookingFrom('n')) ) + getValue('n') + Console.RESET
    var ul = "└"
    var ur = "┘"
    if (getCornersLookingFrom('s').contains('w')) ul = u
    if (getCornersLookingFrom('s').contains('e')) ur = u
    s" $ul $u $ur"
  }
}

