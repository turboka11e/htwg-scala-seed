package de.htwg.se.Carcassonne.model

import org.scalatest._

class PointsSpec extends WordSpec with Matchers {

  "Points calculates the Points of the Player. Territories " when {
    "processed in first Level " should {

      val terriList:List[List[(Int, Area)]] = List(List((0, Area('c', List('n'), 0)), (0, Area('c', List('n'), 2))),
        List((1, Area('r', List('n'), 2)), (2, Area('r', List('w', 'e', 's'), 1))), List((1, Area('g', List('n'), 2)), (0, Area('g', List('w'), -1))))

      val players = List(Player("Rick"), Player("Morty"), Player("Beth"))

      val points = Points(terriList, players)

      "return Lists of different calculated Parameters" in {

        points.listOfFinishedTerris should be(List(List(1.0), List(0.0), List(0.0)))

        points.playersToList should be(List(List(0, 2), List(2, 1), List(2)))

        points.typeToPointsList should be(List(List(2.0), List(1.0), List(0.0)))

        points.totalPointsOfTerris should be(List(List(4.0), List(2.0), List(0.0)))

        points.reducedPointsOfTerris should be(List(4.0))

        points.reducedPlayersOnTerri should be(List(2.0))

        points.adjustedPointsForPlayers should be(List(2.0))

        points.reducedPlayersList should be(List(List(0, 2)))

        points.pointsForPlayersOnTerri should be(List((List(0,2), 2.0)))

        points.updatePoints() should be(List(Player("Rick", 2.00), Player("Morty"), Player("Beth", 2.0)))
      }
    }
  }


}
