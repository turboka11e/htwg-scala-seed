package de.htwg.se.Carcassonne.model.fileIOComponent

import de.htwg.se.Carcassonne.model.playfieldComponent.playfieldBaseImpl.Playfield
import org.scalatest.{Matchers, WordSpec}

class FileIOSpec extends WordSpec with Matchers {

  "FilIO" when {
    "called playing a Game" should {

      var playfield = new Playfield()
      playfield = playfield.fieldSize(2)
      playfield = playfield.addPlayer("Lukas")
      playfield = playfield.addPlayer("Basti")
      playfield = playfield.getFreshCard(14)
      playfield = playfield.rotateR
      playfield = playfield.selectArea(1)
      playfield = playfield.placeCard(0, 0)
      playfield = playfield.getFreshCard(14)
      playfield = playfield.placeCard(0, 1)

      "save and load with XML" in {
        val fileIO = new fileIoJsonImpl.FileIO()
        fileIO.save(playfield)
        fileIO.load should be(playfield)
      }
      "save and load with Json" in {
        val fileIO = new fileIoXmlImpl.FileIO()
        fileIO.save(playfield)
        fileIO.load should be(playfield)
      }
    }

  }

}