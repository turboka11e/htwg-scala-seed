package de.htwg.se.Carcassonne.database.databaseComponent.slickImpl

import de.htwg.se.Carcassonne.database.DatabaseInterface
import de.htwg.se.Carcassonne.database.dao.PlayerDaoInterface
import de.htwg.se.Carcassonne.database.databaseComponent.slickImpl.dao.PlayerDao
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration
import scala.util.Success

class DatabaseSlick extends DatabaseInterface with DatabaseSchema with InitialData {

  val db = Database.forConfig("board-games")

  val future: Future[Unit] = db.run(players.schema.createIfNotExists).andThen {
    case Success(_) => println("Schema created")
  }
  future.flatMap(_ => insertInitialData())
  Await.ready(future, Duration.Inf)

  override def getPlayerDao: PlayerDaoInterface = PlayerDao(db)
}
