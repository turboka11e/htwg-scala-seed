package de.htwg.se.Carcassonne.model

case class Grid(private val cells:Matrix[Card], private val territories: List[List[(Int, Area)]] = Nil) {

  def this(size: Int) = this(new Matrix[Card](size))

  val size: Int = cells.size

  def getCount:Int = cells.getCount

  def card(row: Int, col: Int): Card = cells.card(row, col)

  def set(row: Int, col: Int, newCard:Card): Grid =
    copy(cells.replaceCell(row, col, newCard))

  def place(row: Int, col: Int, newCard:Card): Grid = {
    if(!cells.checkSet(row, col, newCard) && cells.getCount > 0){
      copy()
    } else {
      val tmp = copy(cells = cells.replaceCell(row, col, newCard))
      tmp.copy(territories = addCardToTerri(row, col, newCard, tmp.cells))
    }
  }

  def addCardToTerri(row: Int, col: Int, newCard: Card, newestCells:Matrix[Card]):List[List[(Int, Area)]] = {

    var tmpList:List[List[(Int, Area)]] = territories

    for(dir <- List('n', 's', 'w', 'e')){
      tmpList = tmpTerriList(row:Int, col:Int, dir:Char, newCard, tmpList)
    }

    tmpList = tmpList.map(p => updateTerriEnv(p, newestCells))

    //print(tmpList)
    tmpList

  }

  def lookNeighbours(row:Int, col:Int, dir:Char, newCard: Card):Option[List[Area]] = {
    val currentArea = newCard.getAreaLookingFrom(dir)
    var col_ind:List[Area] = Nil
    for (x <- currentArea.getCorners) {
      val neighbor = cells.getDirEnv(row, col, x)
      /* Schaue ob in der Richtung eine Karte ist */
      if (neighbor.nonEmpty) {
        /* Speicher die Area in List col_ind */
        col_ind = neighbor.get::col_ind
      }
    }
    if(col_ind.isEmpty) None else Some(col_ind)
  }

  def insertNeighbourTerrisInJoinedTerri(tmpTerri:List[List[(Int, Area)]], col_ind:List[Area]):List[(Int, Area)] = {
    var joinedTerri:List[(Int, Area)] = Nil
    for(x <- col_ind){
      if(!joinedTerri.exists(p => p._2.equals(x))){
        val id = tmpTerri.indexWhere(p => p.exists(b => b._2.equals(x)))
        joinedTerri = joinedTerri:::tmpTerri.apply(id)
      }
    }
    joinedTerri
  }

  def tmpTerriList(row:Int, col:Int, dir:Char, newCard: Card, list:List[List[(Int, Area)]]):List[List[(Int, Area)]] = {

    var tmpTerri = list
    val currentArea = newCard.getAreaLookingFrom(dir)
    var openCorners = 0

    val col_ind:Option[List[Area]] = lookNeighbours(row, col, dir, newCard)

    if(col_ind.isDefined){
      /* Füge die Umgebende Area List in neue List joined Terri hinein */
      var joinedTerri = insertNeighbourTerrisInJoinedTerri(tmpTerri, col_ind.get)

      /* Lösche die alten Territorien in der Hauptliste */
      for(x <- col_ind.get){
        val id = tmpTerri.indexWhere(p => p.exists(b => b._2.equals(x)))
        if(id >= 0) tmpTerri = tmpTerri.filter(_.ne(tmpTerri.apply(id)))
      }

      /* Füge die aktuelle Karte hinzu, falls noch nicht in der JoinedTerri*/
      if(!joinedTerri.exists(p => p._2.equals(currentArea))){
        joinedTerri = (openCorners, currentArea)::joinedTerri
      }

      //joinedTerri = updateTerriEnv(joinedTerri)

      tmpTerri = joinedTerri::tmpTerri
    } else {
      if (!tmpTerri.exists(p => p.exists(p => p._2.equals(newCard.getAreaLookingFrom(dir))))) {
        tmpTerri = List((openCorners, newCard.getAreaLookingFrom(dir))) :: tmpTerri
      }
    }

    tmpTerri
  }

  def getTerritories:List[List[(Int, Area)]] = territories

  def updateTerriEnv(terriEnv:List[(Int, Area)], newestCells:Matrix[Card]):List[(Int, Area)] = {

    var tmpTerriEnv = terriEnv

    for ((y, i) <- terriEnv.zipWithIndex) {
      var freeCorners = 0
      for (x <- y._2.getCorners) {

        //val neighbor = cells.getDirEnv(y._2.xy._1, y._2.xy._2, x)

          /* Schaue ob in der Richtung eine Karte ist */
          if (newestCells.getDirEnv(y._2.xy._1, y._2.xy._2, x).isEmpty) {
            /*  */
            //print(newestCells.getDirEnv(y._2.xy._1, y._2.xy._2, x).getValue + " " + y._2.getValue + " " + y._2.getCorners + " " + y._2.xy + "\n")
            freeCorners += 1
          }

      }
      tmpTerriEnv = tmpTerriEnv.updated(i, (freeCorners, tmpTerriEnv.apply(i)._2))
    }
    tmpTerriEnv
  }




}
