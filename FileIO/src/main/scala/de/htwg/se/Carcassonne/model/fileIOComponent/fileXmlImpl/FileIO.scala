package de.htwg.se.Carcassonne.model.fileIOComponent.fileXmlImpl

import de.htwg.se.Carcassonne.model.fileIOComponent.FileIOInterface

class FileIO extends FileIOInterface {

  override def load: String = {

    val xml = scala.xml.XML.loadFile("playfield.xml")
    xml.toString()
  }

  override def save(playfield: String): Unit = {
    val elem = scala.xml.XML.loadString(playfield)
    scala.xml.XML.save("playfield.xml", elem)
  }

}
