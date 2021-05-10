package de.htwg.se.Carcassonne.database.mongoDbImpl.dao

import de.htwg.se.Carcassonne.model.playerComponent.Player
import org.mongodb.scala.bson.{Document, ObjectId}

class PlayerDao {

  def createPlayer(player: Player): Document = {
    Document("_id" -> player.name, "points" -> player.points)
  }

}
