package de.htwg.se.Carcassonne.model.fileIOComponent.fileIoXmlImpl

import de.htwg.se.Carcassonne.model.fileIOComponent.FileIOInterface
import de.htwg.se.Carcassonne.model.playfieldComponent.PlayfieldInterface

import scala.xml.Elem

class FileIO extends FileIOInterface {

  def load: PlayfieldInterface = {
    val playfield: PlayfieldInterface = null
    playfield
  }
  def save(playfieldInterface: PlayfieldInterface): PlayfieldInterface = {
    val playfield: PlayfieldInterface = null
    playfield
  }

  def playersToXML(playfield: PlayfieldInterface):Elem = {
    <players>
      {
        for {
          p <- playfield.getPlayers
        } yield <player>
          <name>{p.name}</name>
          <points>{p.points}</points>
        </player>
      }
    </players>
  }




}
