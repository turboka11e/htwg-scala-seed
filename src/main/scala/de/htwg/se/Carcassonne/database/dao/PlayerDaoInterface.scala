package de.htwg.se.Carcassonne.database.dao

import de.htwg.se.Carcassonne.model.playerComponent.Player

import scala.concurrent.Promise

trait PlayerDaoInterface {

  def createPlayer(player: Player): Unit

  def readPlayers(): Promise[Seq[Player]]

  def updatePoints(player: Player): Unit

}
