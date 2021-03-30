package de.htwg.se.Carcassonne.model.gridComponent.gridBaseImpl

import de.htwg.se.Carcassonne.model.gridComponent.{AreaInterface, CardInterface, MatrixInterface}

case class Matrix(rows: Vector[Vector[CardInterface]]) extends MatrixInterface {
  def this(size: Int) = this(Vector.tabulate(size, size) { (row, col) => new Card((row, col)) })

  def size: Int = rows.size

  def getCount: Int = {
    // todo function count -- DONE!

    //var count = 0
    //rows.foreach(p => p.foreach(c => if (!c.isEmpty) count += 1))
    //count

    rows.map(vector => vector.count(card => !card.isEmpty)).sum
  }

  def card(row: Int, col: Int): CardInterface = rows(row)(col)

  def replaceCell(row: Int, col: Int, card: CardInterface): Matrix = {
    copy(rows.updated(row, rows(row).updated(col, card)))
  }

  def checkEdge(row: Int, col: Int, dir: Char): Boolean = {
    dir match {
      case 'n' => col > 0
      case 's' => col < size - 1
      case 'w' => row > 0
      case 'e' => row < size - 1
    }
  }

  def checkEnvEmpty(row: Int, col: Int, dir: Char): Boolean = {

    if (checkEdge(row, col, dir)) {
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

  def checkEnv(row: Int, col: Int, dir: Char, checkCard: CardInterface): Boolean = {
    if (!checkEnvEmpty(row, col, dir)) {
      dir match {
        case 'n' => card(row, col - 1).getValue('s').equals(checkCard.getValue('n'))
        case 's' => card(row, col + 1).getValue('n').equals(checkCard.getValue('s'))
        case 'w' => card(row - 1, col).getValue('e').equals(checkCard.getValue('w'))
        case 'e' => card(row + 1, col).getValue('w').equals(checkCard.getValue('e'))
      }
    } else {
      true
    }
  }

  def getDirEnv(row: Int, col: Int, dir: Char): Option[AreaInterface] = {
    if (!checkEnvEmpty(row, col, dir)) {
      dir match {
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
    // todo remove var -- DONE with foldLeft

    //var check = false
    //for (x <- List('n', 's', 'w', 'e')) {
    //check = check || !checkEnvEmpty(row, col, x)
    //}
    //check

    List('n', 's', 'w', 'e').map(c => !checkEnvEmpty(row, col, c)).foldLeft(false)(_ || _)
  }

  def checkSet(row: Int, col: Int, checkCard: CardInterface): Boolean = {
    // todo remove var -- DONE with forall(true && _)

    //    var check = true
    //    for (x <- List('n', 's', 'w', 'e')) {
    //      check = check && checkEnv(row, col, x, checkCard)
    //    }
    //    check && card(row, col).isEmpty && hasNeighbor(row, col)

    List('n', 's', 'w', 'e').map(c => checkEnv(row, col, c, checkCard)).forall(true && _) && card(row, col).isEmpty && hasNeighbor(row, col)
  }
}
