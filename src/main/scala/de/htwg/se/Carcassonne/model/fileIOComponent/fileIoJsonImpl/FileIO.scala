package de.htwg.se.Carcassonne.model.fileIOComponent.fileIoJsonImpl

import de.htwg.se.Carcassonne.model.fileIOComponent.FileIOInterface
import de.htwg.se.Carcassonne.model.playfieldComponent.PlayfieldInterface

class FileIO extends FileIOInterface {
  override def load: PlayfieldInterface = ???

  override def save(playfield: PlayfieldInterface): Unit = ???
}
