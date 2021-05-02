package de.htwg.se.Carcassonne.database

import de.htwg.se.Carcassonne.model.playerComponent.Player
import slick.jdbc.H2Profile.api._

trait PlayerDBInterface {

  def createPlayer(player: Player): Option[Player]

  def readPlayers(): List[Player]

  def updatePlayer(player: Player): Option[Player]

  def deletePlayer(player: Player): Option[Player]

  class Players(tag: Tag) extends Table[Player](tag, "PLAYERS") {

    def name = column[String]("NAME", O.PrimaryKey)

    def points = column[Double]("POINTS")

    def * = (name, points) <> (Player.tupled, Player.unapply)
  }
}
