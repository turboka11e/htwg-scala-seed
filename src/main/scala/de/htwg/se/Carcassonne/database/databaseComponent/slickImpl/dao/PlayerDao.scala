package de.htwg.se.Carcassonne.database.databaseComponent.slickImpl.dao

import de.htwg.se.Carcassonne.database.dao.PlayerDaoInterface
import de.htwg.se.Carcassonne.database.databaseComponent.slickImpl.DatabaseSchema
import de.htwg.se.Carcassonne.model.playerComponent.Player
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.{Await, Future, Promise}
import scala.concurrent.duration.Duration

case class PlayerDao(db: Database) extends PlayerDaoInterface with DatabaseSchema {

  def createPlayer(player: Player): Unit = {
    val addPlayer = players returning players.map(_.name) into ((player, name) => player.copy(name = name)) += player
    db.run(addPlayer)
  }

  def readPlayers(): Promise[Seq[Player]] = {
    val promise = Promise[Seq[Player]]()
    promise.completeWith(db.run(players.result))
    promise
  }

  def updatePoints(player: Player): Unit = {
    val updatePoints = players.filter(r => r.name === player.name).map(_.points).update(player.points)
    db.run(updatePoints)
  }
}
