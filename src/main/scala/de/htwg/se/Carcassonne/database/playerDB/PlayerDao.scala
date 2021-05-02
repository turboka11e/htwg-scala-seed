package de.htwg.se.Carcassonne.database.playerDB

import de.htwg.se.Carcassonne.database.PlayerDBInterface
import de.htwg.se.Carcassonne.model.playerComponent.Player

class PlayerDao extends PlayerDBInterface{

  override def createPlayer(player: Player): Option[Player] = ???

  override def readPlayers(): List[Player] = ???

  override def updatePlayer(player: Player): Option[Player] = ???

  override def deletePlayer(player: Player): Option[Player] = ???
}
