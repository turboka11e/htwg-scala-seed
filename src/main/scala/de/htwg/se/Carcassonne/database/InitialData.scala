package de.htwg.se.Carcassonne.database

import de.htwg.se.Carcassonne.model.playerComponent.Player
import slick.dbio.DBIO
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

trait InitialData {

  self: DatabaseSchema =>

  def db: Database

  def insertInitialData(): Future[Unit] ={
    val setup = DBIO.seq(
      players.delete,
    )
    db.run(setup).andThen {
      case Success(_) => println("Initial data inserted")
      case Failure(e) => println(s"Initial data not inserted: ${e.getMessage}")
    }
  }

}
