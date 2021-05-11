package de.htwg.se.Carcassonne.database.databaseComponent.mongoDbImpl

import de.htwg.se.Carcassonne.database.DatabaseInterface
import de.htwg.se.Carcassonne.database.dao.PlayerDaoInterface
import de.htwg.se.Carcassonne.database.databaseComponent.mongoDbImpl.dao.PlayerDao
import org.mongodb.scala.{Completed, Document, MongoClient, MongoCollection, MongoDatabase, Observer}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class DatabaseMongoDb extends DatabaseInterface {

  val client: MongoClient = MongoClient("mongodb://root:example@localhost:27017")
  val database: MongoDatabase = client.getDatabase("Carcassonne")
  val playerCollection = "Player"
  dropCollection(playerCollection)
  createCollection(playerCollection)

  val collection: MongoCollection[Document] = database.getCollection("Player")

  def createCollection(collection: String): Unit = {
    database.createCollection(collection).subscribe(new Observer[Completed] {
      override def onNext(result: Completed): Unit = println(result)

      override def onError(e: Throwable): Unit = println(e)

      override def onComplete(): Unit = println("Completed creating collection")
    })
  }

  def dropCollection(collection: String): Unit = {
    val drop = database.getCollection(collection).drop()
    drop.subscribe(new Observer[Completed] {
      override def onNext(result: Completed): Unit = println(result)

      override def onError(e: Throwable): Unit = println(e)

      override def onComplete(): Unit = println(s"Dropped collection $collection")
    })
    Await.result(drop.toFuture(), Duration.Inf)
  }

  override def getPlayerDao: PlayerDaoInterface = PlayerDao(collection)
}
