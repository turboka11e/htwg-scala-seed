package de.htwg.se.Carcassonne.database.mongoDbImpl

import de.htwg.se.Carcassonne.database.mongoDbImpl.dao.PlayerDao
import de.htwg.se.Carcassonne.model.playerComponent.Player
import org.mongodb.scala.{Completed, Document, MongoClient, MongoCollection, MongoCredential, MongoDatabase, Observer}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object DatabaseMongoDb extends PlayerDao {

  val client: MongoClient = MongoClient("mongodb://root:example@localhost:27017")
  val database: MongoDatabase = client.getDatabase("Carcassonne")
  val collection: MongoCollection[Document] = database.getCollection("Player")

  val document: Document = createPlayer(Player("Max"))

  val insertObservable = collection.insertOne(document)

  insertObservable.subscribe(new Observer[Completed] {
    override def onNext(result:  _root_.org.mongodb.scala.Completed): Unit = println(s"onNext: $result")

    override def onError(e: Throwable): Unit = println(s"Error $e")

    override def onComplete(): Unit = println(s"onComplete")
  })

  val insertAndCount = for {
    countResult <- collection.countDocuments()
  } yield countResult
  val count = insertAndCount.head()
  count.onComplete {
    case Success(value) => println(value)
    case Failure(exception) => println(exception)
  }
}
