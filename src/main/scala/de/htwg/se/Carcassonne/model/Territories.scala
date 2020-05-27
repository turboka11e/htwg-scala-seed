package de.htwg.se.Carcassonne.model

case class Territories(territories: List[List[Area]] = Nil) {

  def addCard(grid: Grid, row: Int, col: Int, newCard: Card):Territories = {

    var tmpList:List[List[Area]] = territories

    for(dir <- List('n', 's', 'w', 'e')){
      tmpList = tmpTerriList(grid, row:Int, col:Int, dir:Char, newCard, tmpList)
    }

    Territories(tmpList)
    }

  def tmpTerriList(grid: Grid, row:Int, col:Int, dir:Char, newCard: Card, list:List[List[Area]]):List[List[Area]] = {
    val areaOfDir = newCard.getAreaLookingFrom(dir)
    var tmpTerri = list

    if (grid.getDirEnv(row, col, dir) != Area()) {

      val index = tmpTerri.indexWhere(p => p.contains(grid.getDirEnv(row, col, dir)))
      val coalesced = areaOfDir::tmpTerri.find(p => p.contains(grid.getDirEnv(row, col, dir))).get
      tmpTerri = tmpTerri.updated(index, coalesced)

    } else {
      val tmpElementList = List(areaOfDir)
      if(!tmpTerri.exists(p => p.contains(areaOfDir))) {
        tmpTerri = tmpElementList :: tmpTerri
      }
    }

    tmpTerri
  }

}
