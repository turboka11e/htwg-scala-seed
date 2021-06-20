package de.htwg.se.Carcassonne.model.fileIOComponent.fileJsonImpl

import de.htwg.se.Carcassonne.model.fileIOComponent._
import org.scalatest._

class FileIOSpec extends WordSpec with Matchers {

  "A playfield" when {
    RestControllerFileIO
    val fileIO = new fileJsonImpl.FileIO()
    "saved as String" should {
      fileIO.save("a string representation of an playfield")
      val loaded = fileIO.load()
      "be able to save" in {
         loaded should be("a string representation of an playfield")
      }
    }
  }

}
