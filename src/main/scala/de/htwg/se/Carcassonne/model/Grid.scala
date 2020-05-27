package de.htwg.se.Carcassonne.model

case class Grid(private val cells:Matrix[Card]) {

  def this(size: Int) = this(new Matrix[Card](size, Card()))

  val size: Int = cells.size

  def card(row: Int, col: Int): Card = cells.card(row, col)

  def checkEdge(row: Int, col: Int, dir: Char):Boolean = {
    dir match {
      case 'n' => col > 0
      case 's' => col < size - 1
      case 'w' => row > 0
      case 'e' => row < size - 1
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

  def getDirEnv(row: Int, col: Int, dir:Char):Area = {
    if(!checkEnvEmpty(row, col, dir)){
      dir match{
        case 'n' => card(row, col - 1).getAreaLookingFrom('s')
        case 's' => card(row, col + 1).getAreaLookingFrom('n')
        case 'w' => card(row - 1, col).getAreaLookingFrom('e')
        case 'e' => card(row + 1, col).getAreaLookingFrom('w')
      }
    } else {
      Area()
    }
  }

  /*
  def checkPlayer(row: Int, col:Int, dir: Char, checkCard: Card):Boolean = {
    if(!checkEnvEmpty(row, col, dir) && checkCard.getPlayer(dir) != Player("not selected")){
      dir match{
        case 'n' => card(row, col - 1).getPlayer('s').equals(checkCard.getPlayer('n'))
        case 's' => card(row, col + 1).getPlayer('n').equals(checkCard.getPlayer('s'))
        case 'w' => card(row - 1, col).getPlayer('e').equals(checkCard.getPlayer('w'))
        case 'e' => card(row + 1, col).getPlayer('w').equals(checkCard.getPlayer('e'))
      }
    } else {
      true
    }
  }
  */

  def hasNeighbor(row: Int, col: Int): Boolean = {
    var check = false

    for(x <- List('n', 's', 'w', 'e')){
      check = check || !checkEnvEmpty(row, col, x)
    }

    check
  }

  def checkSet(row: Int, col: Int, checkCard:Card): Boolean = {

    var check = true

    for(x <- List('n', 's', 'w', 'e')){
      check = check && checkEnv(row, col, x, checkCard)
    }

    check && card(row, col).isEmpty && hasNeighbor(row, col)
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
