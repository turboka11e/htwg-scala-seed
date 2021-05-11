package de.htwg.se.Carcassonne.database.dao

import de.htwg.se.Carcassonne.model.playerComponent.Player

trait PlayerDaoInterface {

  def createPlayer(player: Player): Unit

  def readPlayers(): List[Player]

  def updatePoints(player: Player): Unit

}
