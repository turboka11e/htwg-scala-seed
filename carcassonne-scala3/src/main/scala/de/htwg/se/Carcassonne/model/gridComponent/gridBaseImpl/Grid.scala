package de.htwg.se.Carcassonne.model.gridComponent.gridBaseImpl

import de.htwg.se.Carcassonne.model.gridComponent.{AreaInterface, CardInterface, GridInterface, MatrixInterface}

case class Grid(private val cells:Matrix[CardInterface], private val territories: List[List[(Int, AreaInterface)]] = Nil) extends GridInterface {

  def this(size: Int) = this(new Matrix[CardInterface](size))

  val size: Int = cells.size

  def getCount:Int = cells.getCount

  def getSize:Int = size

  def card(row: Int, col: Int): CardInterface = cells.card(row, col)

  def set(row: Int, col: Int, newCard:CardInterface): GridInterface = copy(cells.replaceCell(row, col, newCard))

  def place(row: Int, col: Int, newCard:CardInterface): GridInterface = 
    if(!cells.checkSet(row, col, newCard) && cells.getCount > 0)
      copy()
    else 
      val tmp = copy(cells = cells.replaceCell(row, col, newCard))
      tmp.copy(territories = addCardToTerri(row, col, newCard, tmp.cells))


  def addCardToTerri(row: Int, col: Int, newCard: CardInterface, newestCells:MatrixInterface[CardInterface]):List[List[(Int, AreaInterface)]] = 

    var tmpList:List[List[(Int, AreaInterface)]] = territories

    for(dir <- List('n', 's', 'w', 'e'))
      tmpList = tmpTerriList(row:Int, col:Int, dir:Char, newCard, tmpList)
    
    tmpList = tmpList.map(p => updateTerriEnv(p, newestCells))
    tmpList
  

  def lookNeighbours(row:Int, col:Int, dir:Char, newCard: CardInterface):Option[List[AreaInterface]] = 
    val currentArea = newCard.getAreaLookingFrom(dir)
    var col_ind:List[AreaInterface] = Nil
    for (x <- currentArea.getCorners) 
      val neighbor = cells.getDirEnv(row, col, x)
      /* Schaue ob in der Richtung eine Karte ist */
      if (neighbor.nonEmpty) 
        /* Speicher die Area in List col_ind */
        col_ind = neighbor.get::col_ind        
    if(col_ind.isEmpty) None else Some(col_ind)


  def insertNeighbourTerrisInJoinedTerri(tmpTerri:List[List[(Int, AreaInterface)]], col_ind:List[AreaInterface]):List[(Int, AreaInterface)] = 
    var joinedTerri:List[(Int, AreaInterface)] = Nil
    for(x <- col_ind)
      if(!joinedTerri.exists(p => p._2.equals(x)))
        val id = tmpTerri.indexWhere(p => p.exists(b => b._2.equals(x)))
        joinedTerri = joinedTerri:::tmpTerri.apply(id)     
    joinedTerri
  

  def tmpTerriList(row:Int, col:Int, dir:Char, newCard: CardInterface, list:List[List[(Int, AreaInterface)]]):List[List[(Int, AreaInterface)]] = 

    var tmpTerri = list
    val currentArea = newCard.getAreaLookingFrom(dir)
    val openCorners = 0

    val col_ind:Option[List[AreaInterface]] = lookNeighbours(row, col, dir, newCard)

    if(col_ind.isDefined)
      /* Füge die Umgebende Area List in neue List joined Terri hinein */
      var joinedTerri = insertNeighbourTerrisInJoinedTerri(tmpTerri, col_ind.get)

      /* Lösche die alten Territorien in der Hauptliste */
      for(x <- col_ind.get)
        val id = tmpTerri.indexWhere(p => p.exists(b => b._2.equals(x)))
        if(id >= 0) tmpTerri = tmpTerri.filter(_.ne(tmpTerri.apply(id)))
      

      /* Füge die aktuelle Karte hinzu, falls noch nicht in der JoinedTerri*/
      if(!joinedTerri.exists(p => p._2.equals(currentArea)))
        joinedTerri = (openCorners, currentArea)::joinedTerri

      //joinedTerri = updateTerriEnv(joinedTerri)

      tmpTerri = joinedTerri::tmpTerri
    else 
      if (!tmpTerri.exists(p => p.exists(p => p._2.equals(newCard.getAreaLookingFrom(dir))))) 
        tmpTerri = List((openCorners, newCard.getAreaLookingFrom(dir))) :: tmpTerri     
    tmpTerri

  def getTerritories:List[List[(Int, AreaInterface)]] = territories

  def updateTerriEnv(terriEnv:List[(Int, AreaInterface)], newestCells:MatrixInterface[CardInterface]):List[(Int, AreaInterface)] = 

    var tmpTerriEnv = terriEnv

    for ((y, i) <- terriEnv.zipWithIndex) 
      var freeCorners = 0
      for (x <- y._2.getCorners)

        //val neighbor = cells.getDirEnv(y._2.xy._1, y._2.xy._2, x)

          /* Schaue ob in der Richtung eine Karte ist */
          if (newestCells.getDirEnv(y._2.getCoord._1, y._2.getCoord._2, x).isEmpty)
            /*  */
            //print(newestCells.getDirEnv(y._2.xy._1, y._2.xy._2, x).getValue + " " + y._2.getValue + " " + y._2.getCorners + " " + y._2.xy + "\n")
            freeCorners += 1   
      tmpTerriEnv = tmpTerriEnv.updated(i, (freeCorners, tmpTerriEnv.apply(i)._2))   
    tmpTerriEnv


}
