package de.htwg.se.Carcassonne.model

case class Points() {

  def updatePoints(terriList:List[List[(Int, Area)]], players:List[Player]):List[Player] = {

    val closList = terriList.map(l => l.map(a => a._2.getCorners.size).map { case 1 => -1; case 2 => 0; case 3 => 1; case 4 => 2 })
    val reduced = closList.map(l => l.sum :: Nil)
    val openReduced = terriList.map(l => l.head._1).map{case 0 => 1; case _ => 0}.map(l => l.toDouble :: Nil)
    val openfinishedList = reduced.map(l => l.map { case -2 => 1.0; case 0 => 1.0; case _ => 0.0 })

    val finishedList = openReduced.zip(openfinishedList).map(l => l._1 ::: l._2).map(l => l.product :: Nil)

    val playList = terriList.map(l => l.map(a => a._2.player).distinct.filter(a => a != -1))
    val typeList = terriList.map(l => l.map(a => a._2.getValue).distinct.map { case 'c' => 2.0; case 'r' => 1.0; case _ => 0.0 })
    val poinList = terriList.map(l => l.size :: Nil)

    val calcPoin = typeList.zip(poinList).map(l => l._1.sum * l._2.sum :: Nil)

    val reducedCalcPoint = calcPoin.zip(finishedList).filter(p => p._2.head == 1).map(l => l._1 ::: l._2).map(l => l.product)

    val sharedTerri = playList.map(l => l.size.toDouble)
    val reducedTerri = sharedTerri.zip(finishedList).filter(p => p._2.head == 1).map(l => l._1 :: Nil ::: l._2).map(l => l.product)
    val divideCalcPoint = reducedCalcPoint.zip(reducedTerri).map(l => l._1 / l._2)

    val reducedPlayList = playList.zip(finishedList).filter(p => p._2.head == 1).map { a => a._1 }

    val perTerriPlayerPointList = reducedPlayList.zip(divideCalcPoint)

    var perPlayerPointsList: List[Double] = List.fill(players.size)(elem = 0)

    for (terri <- perTerriPlayerPointList) {
      for (player <- terri._1) {
        perPlayerPointsList = perPlayerPointsList.updated(player, perPlayerPointsList.apply(player) + terri._2)
      }
    }

    var newPlayerList:List[Player] = players

    for(x <- perPlayerPointsList.indices){
      newPlayerList = newPlayerList.updated(x, players(x).copy(points = perPlayerPointsList(x)))
    }
    newPlayerList
  }

}
