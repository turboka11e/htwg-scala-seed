package de.htwg.se.Carcassonne.model.fileIOComponent.fileJsonImpl

import de.htwg.se.Carcassonne.model.fileIOComponent.FileIOInterface

import java.io.{File, PrintWriter}
import scala.io.Source

class FileIO extends FileIOInterface {

  override def load(): String = {

    val source = Source.fromFile("playfield.json")
    val string = source.getLines.mkString
    source.close()
    string
  }

  override def save(playfield: String): Unit = {
    val pw = new PrintWriter(new File("playfield.json"))
    pw.write(playfield)
    pw.close()
  }

}


