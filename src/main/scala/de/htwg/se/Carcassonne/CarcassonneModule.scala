package de.htwg.se.Carcassonne

import com.google.inject.AbstractModule
import de.htwg.se.Carcassonne.controller.controllerComponent._
import de.htwg.se.Carcassonne.database.DatabaseInterface
import de.htwg.se.Carcassonne.database.databaseComponent.mongoDbImpl.DatabaseMongoDb
import de.htwg.se.Carcassonne.database.databaseComponent.slickImpl.DatabaseSlick
import de.htwg.se.Carcassonne.model.fileComponent.FileInterface
import de.htwg.se.Carcassonne.model.fileComponent.fileJsonImpl.FileIO
import de.htwg.se.Carcassonne.model.playfieldComponent.{PlayfieldInterface, _}
import net.codingwell.scalaguice.ScalaModule

class CarcassonneModule extends AbstractModule with ScalaModule {

  def configure(): Unit = {
    bind[ControllerInterface].to[controllerBaseImpl.Controller]
    bind[PlayfieldInterface].to[playfieldBaseImpl.Playfield]
    //    bind[FileIOInterface].to[fileIoXmlImpl.FileIO]
    bind[FileInterface].to[FileIO]
//    bind[DatabaseInterface].to[DatabaseSlick]
    bind[DatabaseInterface].to[DatabaseMongoDb]
  }
}
