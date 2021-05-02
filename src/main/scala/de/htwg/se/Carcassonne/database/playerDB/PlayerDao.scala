package de.htwg.se.Carcassonne.database.playerDB

import de.htwg.se.Carcassonne.database.PlayerDBInterface
import de.htwg.se.Carcassonne.model.playerComponent.Player
import slick.jdbc.H2Profile.api._

import scala.concurrent.Future

class PlayerDao(db: Database ) extends PlayerDBInterface{

  def createPlayer(player: Player): Future[Player] = {
    val addPlayer = players returning players.map(_.name) into ((player, name) => player.copy(name = name)) += player
    db.run(addPlayer)
  }

  def readPlayers(): List[Player] = ???

  def updatePlayer(player: Player): Future[Player] = ???

  def deletePlayer(player: Player): Future[Player] = ???
}
