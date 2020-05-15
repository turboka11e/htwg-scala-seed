package de.htwg.se.Carcassonne.model

case class Grid(private val cells:Matrix[Card]) {

  def this(size: Int) = this(new Matrix[Card](size, Card()))

  val size: Int = cells.size

  def cell(row: Int, col: Int): Card = cells.card(row, col)

  def set(row: Int, col: Int, nord:Char, west:Char, east:Char, south:Char): Grid = copy(cells.replaceCell(row, col, Card(nord, west, east, south)))

  def printgrid():String = {

    var tmpstring:String = ""

    for(yy <- 0 until size){

      for (xx <- 0 until size) {
        val o = cell(xx, yy).nord
        tmpstring += s" ┌ $o ┐"
      }
      tmpstring += s"\n"

      for (xx <- 0 until size) {
        val l = cell(xx, yy).west
        val r = cell(xx, yy).east
        tmpstring += s" $l   $r"
      }
      tmpstring += "\n"

      for (xx <- 0 until size) {
        val u = cell(xx, yy).south
        tmpstring += s" └ $u ┘"
      }
      tmpstring += "\n"
    }

    tmpstring
  }

}
