package de.htwg.se.Carcassonne.model.playfieldComponent.playfieldBaseImpl

import de.htwg.se.Carcassonne.model.gridComponent.AreaInterface
import de.htwg.se.Carcassonne.model.playerComponent.Player

case class Points(terriList:List[List[(Int, AreaInterface)]], players:List[Player]) {

  def listOfFinishedTerris:List[List[Double]] =
    terriList.map(l => l.map(a => a._1.toDouble)).map(l => l.distinct.sum :: Nil).map(p => p.map {case 0 => 1.0; case _ => 0.0})

  def playersToList:List[List[Int]] = terriList.map(l => l.map(a => a._2.getPlayer).distinct.filter(a => a != -1))

  def typeToPointsList:List[List[Double]] = terriList.map(l => l.map(a => a._2.getValue).distinct.map { case 'c' => 2.0; case 'r' => 1.0; case _ => 0.0 })

  def totalPointsOfTerris:List[List[Double]] = typeToPointsList.zip(terriList.map(l => l.size :: Nil)).map(l => l._1.sum * l._2.sum :: Nil)

  def reducedPointsOfTerris:List[Double] = totalPointsOfTerris.zip(listOfFinishedTerris).filter(p => p._2.head == 1).map(l => l._1 ::: l._2).map(l => l.product)

  def reducedPlayersOnTerri:List[Double] =
    playersToList.map(l => l.size.toDouble).zip(listOfFinishedTerris).filter(p => p._2.head == 1).map(l => l._1 :: Nil ::: l._2).map(l => l.product)

  def adjustedPointsForPlayers:List[Double] = reducedPointsOfTerris.zip(reducedPlayersOnTerri).map(l => l._1 / l._2)

  def reducedPlayersList:List[List[Int]] = playersToList.zip(listOfFinishedTerris).filter(p => p._2.head == 1).map { a => a._1 }

  def pointsForPlayersOnTerri: List[(List[Int], Double)] = reducedPlayersList.zip(adjustedPointsForPlayers)

  def updatePoints():List[Player] = {

    var perPlayerPointsList: List[Double] = List.fill(players.size)(elem = 0)

    for (terri <- pointsForPlayersOnTerri)
      for (player <- terri._1)
        perPlayerPointsList = perPlayerPointsList updated (player, perPlayerPointsList.apply(player) + terri._2)

    var newPlayerList:List[Player] = players

    for(x <- perPlayerPointsList.indices)
      newPlayerList = newPlayerList updated (x, players(x) copy (points = perPlayerPointsList(x)) )
    newPlayerList
  }

}
