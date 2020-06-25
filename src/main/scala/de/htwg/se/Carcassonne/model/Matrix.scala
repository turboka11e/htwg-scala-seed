package de.htwg.se.Carcassonne.model

case class Matrix[T] (rows:Vector[Vector[Card]], private val count:Int = 0) {
  def this(size:Int) = this(Vector.tabulate(size, size){(row, col) => new Card((row, col))})

  val size:Int = rows.size

  def getCount:Int = count

  def card(row:Int, col:Int):Card = rows (row)(col)

  def replaceCell(row:Int, col:Int, cell:Card):Matrix[Card] = {
    copy(rows.updated(row, rows(row).updated(col, cell)), count + 1)
  }

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

  def getDirEnv(row: Int, col: Int, dir:Char):Option[Area] = {
    if(!checkEnvEmpty(row, col, dir)){
      dir match{
        case 'n' => Some(card(row, col - 1).getAreaLookingFrom('s'))
        case 's' => Some(card(row, col + 1).getAreaLookingFrom('n'))
        case 'w' => Some(card(row - 1, col).getAreaLookingFrom('e'))
        case 'e' => Some(card(row + 1, col).getAreaLookingFrom('w'))
      }
    } else {
      None
    }
  }

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
}
