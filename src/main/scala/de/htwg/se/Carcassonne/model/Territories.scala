package de.htwg.se.Carcassonne.model

case class Territories(territories: List[List[Area]] = Nil, private val points:List[Double] = List(0, 0, 0, 0, 0, 0, 0, 0, 0)) {

  def addCard(grid: Grid, row: Int, col: Int, newCard: Card):Territories = {

    var tmpList:List[List[Area]] = territories

    for(dir <- List('n', 's', 'w', 'e')){
      tmpList = tmpTerriList(grid, row:Int, col:Int, dir:Char, newCard, tmpList)
    }

    copy(tmpList, updatePoints(tmpList))

    }

  private def tmpTerriList(grid: Grid, row:Int, col:Int, dir:Char, newCard: Card, list:List[List[Area]]):List[List[Area]] = {

    var tmpTerri = list
    val currentArea = newCard.getAreaLookingFrom(dir)

    var col_ind:List[Area] = List() // sammle angrenzende Areas

    /* Schau in alle Richtungen der Area */
    for (x <- currentArea.getCorners) {
      val neighbor = grid.getDirEnv(row, col, x)
      /* Schaue ob in der Richtung eine Karte ist */
      if (neighbor != Area()) {
        /* Speicher die Area in List col_ind */
        col_ind = neighbor::col_ind
      }
    }

    if(col_ind.nonEmpty){
      var joinedTerri:List[Area] = Nil
      for(x <- col_ind){
        val id = tmpTerri.indexWhere(p => p.exists(b => b.eq(x)))
        joinedTerri = joinedTerri:::tmpTerri.apply(id)
      }
      for(x <- col_ind){
        val id = tmpTerri.indexWhere(p => p.exists(b => b.eq(x)))
        tmpTerri = tmpTerri.filter(_.ne(tmpTerri.apply(id)))
      }
      if(!joinedTerri.exists(p => p.eq(currentArea))){
        joinedTerri = currentArea::joinedTerri
      }
      tmpTerri = joinedTerri::tmpTerri
    } else {
      if (!tmpTerri.exists(p => p.exists(p => p.eq(newCard.getAreaLookingFrom(dir))))) {
        tmpTerri = List(newCard.getAreaLookingFrom(dir)) :: tmpTerri
      }
    }

    tmpTerri
  }

  def getTerritories:List[List[Area]] = territories

  def getPoints:List[Double] = points

  def updatePoints(terriList:List[List[Area]]):List[Double] = {

    val closList = terriList.map(l => l.map(a => a.getCorners.size).map { case 1 => -1; case 2 => 2; case 3 => 3; case 4 => 4 })
    val reduced = closList.map(l => l.sum :: Nil)
    val finishedList = reduced.map(l => l.map { case -2 => 1.0; case 0 => 1.0; case _ => 0.0 })

    val playList = terriList.map(l => l.map(a => a.player).distinct.filter(a => a != -1))
    val typeList = terriList.map(l => l.map(a => a.getValue).distinct.map { case 'c' => 2.0; case 'r' => 1.0; case _ => 0.0 })
    val poinList = terriList.map(l => l.size :: Nil)

    val calcPoin = typeList.zip(poinList).map(l => l._1.sum * l._2.sum :: Nil)

    val reducedCalcPoint = calcPoin.zip(finishedList).filter(p => p._2.head == 1).map(l => l._1 ::: l._2).map(l => l.product)

    val sharedTerri = playList.map(l => l.size.toDouble)
    val reducedTerri = sharedTerri.zip(finishedList).filter(p => p._2.head == 1).map(l => l._1 :: Nil ::: l._2).map(l => l.product)
    val divideCalcPoint = reducedCalcPoint.zip(reducedTerri).map(l => l._1 / l._2)

    val reducedPlayList = playList.zip(finishedList).filter(p => p._2.head == 1).map { a => a._1 }

    val perTerriPlayerPointList = reducedPlayList.zip(divideCalcPoint)

    var perPlayerPointsList: List[Double] = List(0, 0, 0, 0, 0, 0, 0, 0, 0)

    for (terri <- perTerriPlayerPointList) {
      for (player <- terri._1) {
        perPlayerPointsList = perPlayerPointsList.updated(player, perPlayerPointsList.apply(player) + terri._2)
      }
    }
    perPlayerPointsList
  }









  /*
  override def toString:String = {
    var tmpString = ""
    for(x <- territories){
      val areaType = x.head.getValue
      val terrisize = x.size
      var namelist = ""
      for (elem <- x.filterNot(name => name.getPlayer.equals(Player("")))) {
        namelist+= elem.getPlayer.name + ", "
      }

      tmpString += s"$areaType $terrisize $namelist\n"
    }
    tmpString
  }

   */
}
