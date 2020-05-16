package de.htwg.se.Carcassonne.model

case class Grid(private val cells:Matrix[Card]) {

  def this(size: Int) = this(new Matrix[Card](size, Card()))

  val size: Int = cells.size

  def card(row: Int, col: Int): Card = cells.card(row, col)

  def set(row: Int, col: Int, newCard:Card): Grid =
    copy(cells.replaceCell(row, col, newCard))

  def printgrid():String = {

    var tmpstring:String = ""

    for(yy <- 0 until size){

      for (xx <- 0 until size) {
        val o = card(xx, yy).getValue('n')
        var ol = '┌'
        var or = '┐'
        if(card(xx, yy).getCornersLookingFrom('n').contains('w')) ol = o
        if(card(xx, yy).getCornersLookingFrom('n').contains('e')) or = o
        tmpstring += s" $ol $o $or"
      }
      tmpstring += s"\n"

      for (xx <- 0 until size) {
        val l = card(xx, yy).getValue('w')
        val r = card(xx, yy).getValue('e')
        var m = ' '
        if(card(xx, yy).getCornersLookingFrom('e').contains('w')) m = l
        if(card(xx, yy).getCornersLookingFrom('n').contains('s')) m = card(xx, yy).getValue('n')
        tmpstring += s" $l $m $r"
      }
      tmpstring += "\n"

      for (xx <- 0 until size) {
        val u = card(xx, yy).getValue('s')
        var ul = '└'
        var ur = '┘'
        if(card(xx, yy).getCornersLookingFrom('s').contains('w')) ul = u
        if(card(xx, yy).getCornersLookingFrom('s').contains('e')) ur = u
        tmpstring += s" $ul $u $ur"
      }
      tmpstring += "\n"
    }

    tmpstring
  }

}
