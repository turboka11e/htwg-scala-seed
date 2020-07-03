package de.htwg.se.Carcassonne.model.gridComponent.gridBaseImpl

import de.htwg.se.Carcassonne.model.gridComponent.{AreaInterface, CardInterface}


case class Card(private val areas:Option[List[AreaInterface]] = None, private val id_rotation:Option[(String, Int)] = None) extends CardInterface {

  def isEmpty: Boolean = areas.isEmpty

  def getIDRot: Option[(String, Int)] = id_rotation

  def getValue(dir:String): Option[String] = if(!isEmpty) Option(areas.get.find(_.getCorners.contains(dir)).get.getValue) else None

  def getPlayer(dir:String): Option[Int] = if(!isEmpty) areas.get.find(_.getCorners.contains(dir)).get.getPlayer else None

  def getAreas():Option[List[AreaInterface]] = areas

  def getCornersLookingFrom(dir:String): Option[List[String]] = if(!isEmpty) Option(areas.get.find(_.getCorners.contains(dir)).get.getCorners) else None

  def getAreaLookingFrom(dir:String): Option[AreaInterface] = if(!isEmpty) Option(areas.get.find(_.getCorners.contains(dir)).get) else None

  def rotateRight():CardInterface = {
    val rotatedAreas:List[AreaInterface] = areas.get.map { x => x.rotateRight() }
    var rotations = id_rotation.get._2 + 1
    if(rotations == 4){
     rotations = 0
    }
    Card(Option(rotatedAreas), id_rotation = Option(id_rotation.get._1, rotations))
  }

  override def toString:String = {

    var tmpstring = ""

    val o = getValueString("n")
    var ol = "┌"
    var or = "┐"
    if (containsCornerLookingFromString("n", "w")) ol = o
    if (containsCornerLookingFromString("n", "e")) or = o
    tmpstring += s" $ol $o $or"

    tmpstring += s"\n"

    val l = getValueString("w")
    val r = getValueString("e")
    var m = " "
    if (containsCornerLookingFromString("e", "w")) m = l
    if (containsCornerLookingFromString("n", "s")) m = getValueString("n")
    tmpstring += s" $l $m $r"

    tmpstring += "\n"

    val u = getValueString("s")
    var ul = "└"
    var ur = "┘"
    if (containsCornerLookingFromString("s", "w")) ul = u
    if (containsCornerLookingFromString("s", "e")) ur = u
    tmpstring += s" $ul $u $ur"

    tmpstring += "\n"

    tmpstring
  }

  def getValueString(dir:String): String = if(getValue(dir).isDefined) getValue(dir).get else " "

  def containsCornerLookingFromString(dir:String, look:String): Boolean =
    if(getCornersLookingFrom(dir).isDefined) getCornersLookingFrom(dir).get.contains(look) else false

  private val color = List(Console.BLUE, Console.RED, Console.YELLOW, Console.GREEN)

  def colorDir(dir:String): String = {
    var o = getValueString(dir)
    if(getAreaLookingFrom(dir).isDefined) {
      val index = areas.get.indexWhere(p => p == getAreaLookingFrom(dir))
      o = color(index) + o + Console.RESET
    }
    o
  }

  def topString:String = {
    val o = colorDir("n")
    var ol = "┌"
    var or = "┐"
    if (containsCornerLookingFromString("n", "w")) ol = o
    if (containsCornerLookingFromString("n", "e")) or = o
    s" $ol $o $or"
  }

  def midString:String = {
    val l = colorDir("w")
    val r = colorDir("e")
    var m = " "
    if (containsCornerLookingFromString("e", "w")) m = l
    if (containsCornerLookingFromString("n", "s")) m = colorDir("n")
    s" $l $m $r"
  }

  def lowString:String = {
    val u = colorDir("s")
    var ul = "└"
    var ur = "┘"
    if (containsCornerLookingFromString("s", "w")) ul = u
    if (containsCornerLookingFromString("s", "e")) ur = u
    s" $ul $u $ur"
  }
}
