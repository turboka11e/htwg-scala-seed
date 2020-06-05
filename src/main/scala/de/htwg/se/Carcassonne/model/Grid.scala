package de.htwg.se.Carcassonne.model

case class Grid(private val cells:Matrix[Card], private val territories: List[List[Area]] = Nil) {

  def this(size: Int) = this(new Matrix[Card](size, Card()))

  val size: Int = cells.size

  def getCount:Int = cells.getCount

  def card(row: Int, col: Int): Card = cells.card(row, col)

  def set(row: Int, col: Int, newCard:Card): Grid =
    copy(cells.replaceCell(row, col, newCard))

  def place(row: Int, col: Int, newCard:Card): Grid = {
    if(!cells.checkSet(row, col, newCard) && cells.getCount > 0){
      copy()
    } else {
      copy(cells.replaceCell(row, col, newCard), addCard(row, col, newCard))
    }
  }

  def addCard(row: Int, col: Int, newCard: Card):List[List[Area]] = {

    var tmpList:List[List[Area]] = territories

    for(dir <- List('n', 's', 'w', 'e')){
      tmpList = tmpTerriList(row:Int, col:Int, dir:Char, newCard, tmpList)
    }

    print(tmpList)
    tmpList

  }

  private def tmpTerriList(row:Int, col:Int, dir:Char, newCard: Card, list:List[List[Area]]):List[List[Area]] = {

    var tmpTerri = list
    val currentArea = newCard.getAreaLookingFrom(dir)

    var col_ind:List[Area] = List() // sammle angrenzende Areas

    /* Schau in alle Richtungen der Area */
    for (x <- currentArea.getCorners) {
      val neighbor = cells.getDirEnv(row, col, x)
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
        if(id > 0) tmpTerri = tmpTerri.filter(_.ne(tmpTerri.apply(id)))
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


}
