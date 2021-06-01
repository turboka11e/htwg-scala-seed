package de.htwg.se.Carcassonne.database.databaseComponent.mongoDbImpl.dao

import de.htwg.se.Carcassonne.database.dao.PlayerDaoInterface
import de.htwg.se.Carcassonne.model.playerComponent.Player
import org.mongodb.scala.bson.Document
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.model.Updates.set
import org.mongodb.scala.result.UpdateResult
import org.mongodb.scala.{Completed, MongoCollection, Observer}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future, Promise}

case class PlayerDao(collection: MongoCollection[Document]) extends PlayerDaoInterface {

  override def createPlayer(player: Player): Unit = {
    collection.insertOne(Document("_id" -> player.name, "points" -> player.points)).subscribe(new Observer[Completed] {
      override def onNext(result: Completed): Unit = ()

      override def onError(e: Throwable): Unit = println(e)

      override def onComplete(): Unit = println(s"Inserted ${player.name}")
    })
  }

  override def readPlayers(): Promise[Seq[Player]] = {
    val playerListPromise: Promise[Seq[Player]] = Promise[Seq[Player]]()
    collection.find().subscribe(new Observer[Document] {
      var playerList: List[Player] = Nil

      override def onNext(result: Document): Unit = {
        playerList = Player(result.getString("_id"), result.getDouble("points")) :: playerList
      }

      override def onError(e: Throwable): Unit = playerListPromise.failure(e)

      override def onComplete(): Unit = playerListPromise.success(playerList)
    })
    playerListPromise
  }

  override def updatePoints(player: Player): Unit = {
    collection.updateOne(equal("_id", player.name), set("points", player.points)).subscribe(new Observer[UpdateResult] {
      override def onNext(result: UpdateResult): Unit = println(s"${player.name} $result")

      override def onError(e: Throwable): Unit = println(e)

      override def onComplete(): Unit = ()
    })
  }
}
