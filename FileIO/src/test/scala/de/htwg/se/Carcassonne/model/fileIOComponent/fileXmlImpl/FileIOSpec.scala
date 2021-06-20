package de.htwg.se.Carcassonne.model.fileIOComponent.fileXmlImpl

import de.htwg.se.Carcassonne.model.fileIOComponent._
import org.scalatest._

class FileIOSpec extends WordSpec with Matchers {

  "A playfield" when {
    val fileIO = new fileXmlImpl.FileIO()
    "saved as xml string" should {
      fileIO.save("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><t>a string representation of an playfield</t>")
      val loaded = fileIO.load()
      "be able to save" in {
        loaded should be("<t>a string representation of an playfield</t>")
      }
    }
  }

}
