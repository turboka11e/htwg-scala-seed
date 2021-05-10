package de.htwg.se.Carcassonne.database.slickComponent.dao

import de.htwg.se.Carcassonne.database.slickComponent.DatabaseSchema
import de.htwg.se.Carcassonne.model.playerComponent.Player
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

class PlayerDao(db: Database) extends DatabaseSchema {

  def createPlayer(player: Player): Future[Player] = {
    val addPlayer = players returning players.map(_.name) into ((player, name) => player.copy(name = name)) += player
    db.run(addPlayer)
  }

  def readPlayers(): List[Player] = {
    Await.result(db.run(players.result), Duration.Inf).toList
  }

  def updatePoints(player: Player): Future[Int] = {
    val updatePoints = players.filter(r => r.name === player.name).map(_.points).update(player.points)
    db.run(updatePoints)
  }
}
