package de.htwg.se.Carcassonne

import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule
import de.htwg.se.Carcassonne.controller.controllerComponent._
import de.htwg.se.Carcassonne.model.fileIOComponent._
import de.htwg.se.Carcassonne.model.fileIOComponent.FileIOInterface
import de.htwg.se.Carcassonne.model.playfieldComponent._
import de.htwg.se.Carcassonne.model.playfieldComponent.PlayfieldInterface

class CarcassonneModule extends AbstractModule with ScalaModule {

  def configure(): Unit = {
    bind[ControllerInterface].to[controllerBaseImpl.Controller]
    bind[PlayfieldInterface].to[playfieldBaseImpl.Playfield]
    bind[FileIOInterface].to[fileIoXmlImpl.FileIO]
//    bind[FileIOInterface].to[fileIoJsonImpl.FileIO]
  }
}
