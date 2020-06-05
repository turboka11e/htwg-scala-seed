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
      copy(cells.replaceCell(row, col, newCard), addCardToTerri(row, col, newCard))
    }
  }

  def addCardToTerri(row: Int, col: Int, newCard: Card):List[List[(Int, Area)]] = {

    var tmpList:List[List[(Int, Area)]] = territories

    for(dir <- List('n', 's', 'w', 'e')){
      tmpList = tmpTerriList(row:Int, col:Int, dir:Char, newCard, tmpList)
    }

    tmpList

  }

  private def tmpTerriList(row:Int, col:Int, dir:Char, newCard: Card, list:List[List[(Int, Area)]]):List[List[(Int, Area)]] = {

    var tmpTerri = list
    val currentArea = newCard.getAreaLookingFrom(dir)
    var openCorners = 0

    var col_ind:List[Area] = List() // sammle angrenzende Areas

    /* Schau in alle Richtungen der Area */
    for (x <- currentArea.getCorners) {
      val neighbor = cells.getDirEnv(row, col, x)
      /* Schaue ob in der Richtung eine Karte ist */
      if (neighbor != Area()) {
        /* Speicher die Area in List col_ind */
        col_ind = neighbor::col_ind
      } else {
        openCorners += 1
      }
    }

    if(col_ind.nonEmpty){
      var joinedTerri:List[(Int, Area)] = Nil

      /* Füge die Umgebende Area List in neue List joined Terri hinein */
      for(x <- col_ind){
        if(!joinedTerri.exists(p => p._2.equals(x))){
          val id = tmpTerri.indexWhere(p => p.exists(b => b._2.equals(x)))
          joinedTerri = joinedTerri:::tmpTerri.apply(id)
        }
      }

      /* Lösche die alten Territorien in der Hauptliste */
      for(x <- col_ind){
        val id = tmpTerri.indexWhere(p => p.exists(b => b._2.equals(x)))
        if(id >= 0) tmpTerri = tmpTerri.filter(_.ne(tmpTerri.apply(id)))
      }

      /* Füge die aktuelle Karte hinzu, falls noch nicht in der JoinedTerri*/
      if(!joinedTerri.exists(p => p._2.equals(currentArea))){
        joinedTerri = (openCorners, currentArea)::joinedTerri
      }

      tmpTerri = joinedTerri::tmpTerri
    } else {
      if (!tmpTerri.exists(p => p.exists(p => p._2.equals(newCard.getAreaLookingFrom(dir))))) {
        tmpTerri = List((openCorners, newCard.getAreaLookingFrom(dir))) :: tmpTerri
      }
    }

    tmpTerri
  }

  def getTerritories:List[List[(Int, Area)]] = territories


}
