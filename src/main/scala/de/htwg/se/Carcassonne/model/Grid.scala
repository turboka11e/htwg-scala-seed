package de.htwg.se.Carcassonne.model

case class Grid(private val cells:Matrix[Card]) {

  def this(size: Int) = this(new Matrix[Card](size, Card()))

  val size: Int = cells.size

  def card(row: Int, col: Int): Card = cells.card(row, col)

  def checkSet(row: Int, col: Int, checkCard:Card): Boolean = {
    var check = false

    if(col > 0){ // Prüfe Nord Karte, wenn nicht oberste Karte
      if(!card(row, col - 1).isEmpty) {
        check = card(row, col - 1).getValue('s').equals(checkCard.getValue('n'))
      }
    }
    if(col < size - 1) { // Prüfe Süd Karte, wenn nicht unterste Karte
      if(!card(row, col + 1).isEmpty) {
        check = card(row, col + 1).getValue('n').equals(checkCard.getValue('s'))
      }
    }
    if(row > 0){ // Prüfe West Karte, wenn Karte nicht ganz links
      if(!card(row - 1, col).isEmpty) {
        check = card(row - 1, col).getValue('e').equals(checkCard.getValue('w'))
      }
    }
    if(row < size - 1) { // Prüfe Ost Karte, wenn nicht ganz rechts
      if(!card(row + 1, col).isEmpty) {
        check = card(row + 1, col).getValue('w').equals(checkCard.getValue('e'))
      }
    }
    check && card(row, col).isEmpty
  }

  def set(row: Int, col: Int, newCard:Card): Grid =
    copy(cells.replaceCell(row, col, newCard))

  override def toString:String = {

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
