package de.htwg.se.Carcassonne.model.gridComponent.gridBaseImpl

import de.htwg.se.Carcassonne.model.gridComponent.{AreaInterface, CardInterface, GridInterface}

case class Grid(private val cells: Matrix) extends GridInterface {

  def this(size: Int) = this(new Matrix(size))

  def size: Int = cells.size

  def getCount: Int = cells.getCount

  def card(row: Int, col: Int): CardInterface = cells.card(row, col)

  def set(row: Int, col: Int, newCard: CardInterface): GridInterface = copy(cells.replaceCell(row, col, newCard))

  def place(row: Int, col: Int, newCard: CardInterface): GridInterface = {
    if (!cells.checkSet(row, col, newCard) && cells.getCount > 0) {
      copy()
    }
    else if (!manicanFree(row, col, newCard)) {
      copy()
    } else {
      copy(cells = cells.replaceCell(row, col, newCard))
    }
  }

  def checkSetAndCount(row: Int, col: Int, newCard: CardInterface): Boolean = {
    !cells.checkSet(row, col, newCard) && cells.getCount > 0
  }

  def manicanFree(row: Int, col: Int, newCard: CardInterface): Boolean = {
    val dir = newCard.areas.find(p => p.player != -1)
    if (dir.isDefined) {
      for {
        realDir <- dir.get.corners
      } {
        val neigbor = cells.getDirEnv(row, col, realDir)
        if (neigbor.isDefined) {
          val lookUpTerri = getTerritories.find(p => p.exists(p => p._2.equals(neigbor.get)))
          if (lookUpTerri.exists(p => p.exists(c => c._2.player != -1))) return false
        }
      }
    }
    true
  }

  /* Checks whether the Card can fit in at least on cell with all Rotations covered. Returns false if no placing is possible */
  def placeable(newCard: CardInterface): Boolean = {
    var placeable = false
    for {
      row <- 0 until size
      col <- 0 until size
    } {
      placeable = placeable || cells.checkSet(row, col, newCard)
      placeable = placeable || cells.checkSet(row, col, newCard.rotateRight())
      placeable = placeable || cells.checkSet(row, col, newCard.rotateRight().rotateRight())
      placeable = placeable || cells.checkSet(row, col, newCard.rotateRight().rotateRight().rotateRight())
    }
    placeable
  }

  def getTerritories: List[List[(Int, AreaInterface)]] = {
    var territories: List[List[(Int, AreaInterface)]] = Nil
    for {
      dir <- List('n', 's', 'w', 'e')
      row <- 0 until size
      col <- 0 until size
    } {
      if (!card(row, col).isEmpty && !card(row, col).getValue(dir).equals('g')) {
        territories = areaToTerritoriesProcess(dir, row, col, territories)
      }
    }
    territories
  }

  def areaToTerritoriesProcess(dir: Char, row: Int, col: Int, territories: List[List[(Int, AreaInterface)]]): List[List[(Int, AreaInterface)]] = {
    var tmpTerritories = territories
    val currentArea = cells.card(row, col).getAreaLookingFrom(dir)

    /* Check, whether Area exists in Territories */
    if (!territories.exists(p => p.exists(a => a._2.equals(currentArea)))) {
      /* Process to Add Area to Territories List */
      val neighbours = lookUpNeighbours(dir, row, col)
      if (neighbours.isDefined) {
        val openCorners = currentArea.corners.size - neighbours.get.size
        tmpTerritories = addAreaToTerritories(neighbours, currentArea, territories, openCorners)
      } else {
        tmpTerritories = List((currentArea.corners.size, currentArea)) :: tmpTerritories
      }
    }
    tmpTerritories
  }

  def addAreaToTerritories(neighbours: Option[List[AreaInterface]], currentArea: AreaInterface,
                           territories: List[List[(Int, AreaInterface)]], openCorners: Int): List[List[(Int, AreaInterface)]] = {
    var tmpTerri = territories
    /* Füge die Umgebende Area List in neue List joined Terri hinein */
    var joinedTerri = insertNeighbourTerrisInJoinedTerri(territories, neighbours.get)

    /* Lösche die alten Territorien in der Hauptliste */
    for (x <- neighbours.get) {
      val id = tmpTerri.indexWhere(p => p.exists(b => b._2.equals(x)))
      if (id >= 0) tmpTerri = tmpTerri.filter(_.ne(tmpTerri.apply(id)))
    }

    /* Füge die aktuelle Karte hinzu, falls noch nicht in der JoinedTerri*/
    if (!joinedTerri.exists(p => p._2.equals(currentArea))) {
      joinedTerri = (openCorners, currentArea) :: joinedTerri
    }
    joinedTerri :: tmpTerri
  }

  def insertNeighbourTerrisInJoinedTerri(territories: List[List[(Int, AreaInterface)]], neighbours: List[AreaInterface]): List[(Int, AreaInterface)] = {
    var joinedTerri: List[(Int, AreaInterface)] = Nil
    for (neighbour <- neighbours) {
      if (!joinedTerri.exists(p => p._2.equals(neighbour))) {
        val id = territories.indexWhere(p => p.exists(b => b._2.equals(neighbour)))
        if (id >= 0) joinedTerri = joinedTerri ::: territories.apply(id)
      }
    }
    joinedTerri
  }

  def lookUpNeighbours(dir: Char, row: Int, col: Int): Option[List[AreaInterface]] = {
    val currentArea = card(row, col).getAreaLookingFrom(dir)
    var neighbours: List[AreaInterface] = Nil
    for (x <- currentArea.corners) {
      val neighbor = cells.getDirEnv(row, col, x)
      /* Schaue ob in der Richtung eine Karte ist */
      if (neighbor.nonEmpty) {
        /* Speicher die Area in List col_ind */
        neighbours = neighbor.get :: neighbours
      }
    }
    if (neighbours.isEmpty) None else Some(neighbours)
  }

}
