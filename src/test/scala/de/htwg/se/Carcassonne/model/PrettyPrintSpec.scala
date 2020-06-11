package de.htwg.se.Carcassonne.model

import de.htwg.se.Carcassonne.model
import org.scalatest._

class PrettyPrintSpec extends WordSpec with Matchers {
  "PrettyPrint prints the Matrix and everything in the TUI" when {
    "is created it" should {
        val grid = new Grid(3)
        val pList = List(Player("Mark"))
        val p0 = new PrettyPrint(0, grid, CardCreator(0).randCard(0), pList, 0, true)
        val p1 = new PrettyPrint(1, grid, CardCreator(0).randCard(0), pList, 0, true)
        val p2 = new PrettyPrint(2, grid, CardCreator(0).randCard(0), pList, 0, true)
        val p3 = new PrettyPrint(3, grid, CardCreator(0).randCard(0), pList, 0, true)
        val p4 = new PrettyPrint(4, grid, CardCreator(0).randCard(0), pList, 0, true)
        val p5 = new PrettyPrint(5, grid, CardCreator(0).randCard(0), pList, 0, true)
        "print the player " in {
          p0.playerLine should be(p0.playfieldView)
          p1.playerLine should be()
        }
        "print exception" in {
          p0.illegalPlace should be("")
          p1.illegalPlace should be()
        }
        "print the nameLine" in {
          p0.nameLine should be(p0.playfieldView)
          p1.nameLine should be(p0.playfieldView)
        }
        "print the betweenline" in {
          p0.betweenLine should be(p0.playfieldView)
        }
        "print the freshCardPart" in {
          p0.freshCardPart should be (p0.playfieldView)
        }
        "print the Rest" in {
          p0.restPart should be (p0.playfieldView)
        }
        "print the topRow" in {
          p0.topRow(0) should be (" ┌   ┐ ┌   ┐ ┌   ┐")
          p1.topRow(0) should be (" ┌   ┐ ┌   ┐ ┌   ┐")
          p2.topRow(0) should be (" ┌   ┐ ┌   ┐ ┌   ┐")
          p3.topRow(0) should be (" ┌   ┐ ┌   ┐ ┌   ┐")
          p4.topRow(0) should be (" ┌   ┐ ┌   ┐ ┌   ┐")
          p5.topRow(0) should be (" ┌   ┐ ┌   ┐ ┌   ┐")
0        }
        "print the midRow" in {
          p0.midRow(0) should be ("                  ")
          p1.midRow(0) should be ("                  ")
          p2.midRow(0) should be ("                  ")
          p5.midRow(0) should be ("                  ")
          p5.midRow(0) should be ("                  ")
          p5.midRow(0) should be ("                  ")
        }
        "print the lowRow" in {
          p0.lowRow(0) should be (" └   ┘ └   ┘ └   ┘")
          p1.lowRow(0) should be (" └   ┘ └   ┘ └   ┘")
          p2.lowRow(0) should be (" └   ┘ └   ┘ └   ┘")
          p3.lowRow(0) should be (" └   ┘ └   ┘ └   ┘")
          p4.lowRow(0) should be (" └   ┘ └   ┘ └   ┘")
          p5.lowRow(0) should be (" └   ┘ └   ┘ └   ┘")
        }
        "print the topSeg" in {
          p0.topSeg(0,0) should be (" ┌   ┐")
          p1.topSeg(0,0) should be (" ┌   ┐")
          p2.topSeg(0,0) should be (" ┌   ┐")
          p3.topSeg(0,0) should be (" ┌   ┐")
          p4.topSeg(0,0) should be (" ┌   ┐")
          p5.topSeg(0,0) should be (" ┌   ┐")
        }
        "print the midSeg" in {
          p0.midSeg(0,0) should be ("      ")
          p1.midSeg(0,0) should be ("      ")
          p2.midSeg(0,0) should be ("      ")
          p3.midSeg(0,0) should be ("      ")
          p4.midSeg(0,0) should be ("      ")
          p5.midSeg(0,0) should be ("      ")
        }
        "print the lowSeg" in {
          p0.lowSeg(0,0) should be (" └   ┘")
          p1.lowSeg(0,0) should be (" └   ┘")
          p2.lowSeg(0,0) should be (" └   ┘")
          p3.lowSeg(0,0) should be (" └   ┘")
          p4.lowSeg(0,0) should be (" └   ┘")
          p5.lowSeg(0,0) should be (" └   ┘")
        }
        "get the colored corner" in {
          p0.getColoredCorner(0,0, 'n') should be (" ")
          p0.getColoredCorner(0,0, 'w') should be (" ")
          p0.getColoredCorner(0,0, 'n') should be (" ")
          p0.getColoredCorner(0,0, 's') should be (" ")
          p0.getColoredCorner(0,0, 'n') should be (" ")
          p0.getColoredCorner(0,0, 'n') should be (" ")
        }
    }
  }
}
