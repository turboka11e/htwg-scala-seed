package de.htwg.se.Carcassonne.model

case class Grid(private val cells:Matrix[Card]) {

  def this(size: Int) = this(new Matrix[Card](size, Card()))

  val size: Int = cells.size

  def card(row: Int, col: Int): Card = cells.card(row, col)

  def checkEdge(row: Int, col: Int, dir: Char):Boolean = {
    dir match {
      case 'n' => col > 0
      case 's' => col < size -1
      case 'w' => row > 0
      case 'e' => row < size -1
    }
  }

  def checkEnvEmpty(row: Int, col: Int, dir:Char):Boolean = {
    if(checkEdge(row, col, dir)) {
      dir match {
        case 'n' => card(row, col - 1).isEmpty
        case 's' => card(row, col + 1).isEmpty
        case 'w' => card(row - 1, col).isEmpty
        case 'e' => card(row + 1, col).isEmpty
      }
    } else {
      true
    }
  }

  def checkEnv(row: Int, col: Int, dir: Char, checkCard: Card):Boolean = {
    if(!checkEnvEmpty(row, col, dir)){
      dir match{
        case 'n' => card(row, col - 1).getValue('s').equals(checkCard.getValue('n'))
        case 's' => card(row, col + 1).getValue('n').equals(checkCard.getValue('s'))
        case 'w' => card(row - 1, col).getValue('e').equals(checkCard.getValue('w'))
        case 'e' => card(row + 1, col).getValue('w').equals(checkCard.getValue('e'))
      }
    } else {
      true
    }
  }

  def hasNeighbor(row: Int, col: Int): Boolean = {
    var check = false

    check = check || !checkEnvEmpty(row, col, 'n')
    check = check || !checkEnvEmpty(row, col, 's')
    check = check || !checkEnvEmpty(row, col, 'e')
    check = check || !checkEnvEmpty(row, col, 'w')

    check
  }

  def checkSet(row: Int, col: Int, checkCard:Card): Boolean = {

    var check = true

    check = check && checkEnv(row, col, 'n', checkCard)
    check = check && checkEnv(row, col, 's', checkCard)
    check = check && checkEnv(row, col, 'w', checkCard)
    check = check && checkEnv(row, col, 'e', checkCard)

    check = check && hasNeighbor(row, col)

    check && card(row, col).isEmpty
  }
  /*
  def checkNorth(row: Int, col: Int, checkCard:Card): Boolean =
    card(row, col - 1).getValue('s').equals(checkCard.getValue('n'))

  def checkSouth(row: Int, col: Int, checkCard:Card): Boolean =
    card(row, col + 1).getValue('n').equals(checkCard.getValue('s'))

  def checkWest(row: Int, col: Int, checkCard:Card): Boolean =
    card(row - 1, col).getValue('e').equals(checkCard.getValue('w'))

  def checkEast(row: Int, col: Int, checkCard:Card): Boolean =
    card(row + 1, col).getValue('w').equals(checkCard.getValue('e'))
  */

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
