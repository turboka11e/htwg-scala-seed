package de.htwg.se.Carcassonne.model.gridComponent

object CardinalDirection extends Enumeration {

  protected case class Direction(char: Char) extends super.Val {}

  type CardinalDirection = Direction

  val North: Direction = Direction('n')
  val East: Direction = Direction('e')
  val West: Direction = Direction('w')
  val South: Direction = Direction('s')

  val allDirections: List[CardinalDirection] = List(North, East, West, South)

}
