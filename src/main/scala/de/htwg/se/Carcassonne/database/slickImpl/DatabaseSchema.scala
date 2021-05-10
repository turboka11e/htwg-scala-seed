package de.htwg.se.Carcassonne.database.slickImpl

import de.htwg.se.Carcassonne.model.playerComponent.Player
import slick.jdbc.PostgresProfile.api._

trait DatabaseSchema {

  class Players(tag: Tag) extends Table[Player](tag, "PLAYERS") {

    def name = column[String]("NAME", O.PrimaryKey)

    def points = column[Double]("POINTS")

    def * = (name, points) <> (Player.tupled, Player.unapply)
  }

  def players = TableQuery[Players]
}
