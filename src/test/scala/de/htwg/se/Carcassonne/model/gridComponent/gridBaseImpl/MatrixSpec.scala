package de.htwg.se.Carcassonne.model.gridComponent.gridBaseImpl

import de.htwg.se.Carcassonne.model.gridComponent.CardInterface
import de.htwg.se.Carcassonne.model.playfieldComponent.playfieldBaseImpl.RawCardFactory
import org.scalatest._

class MatrixSpec extends WordSpec with Matchers {
  "A Matrix is a tailor-made immutable data type that contains a two-dimentional Vector of Cells. A Matrix" when {
    "empty " should {
      "be created by using a dimention and a sample cell" in {
        val matrix = new Matrix[Card](2)
        matrix.size should be(2)
      }
      "for test purposes only be created with Size 1" in {
        val testMatrix = new Matrix[CardInterface](1)
        testMatrix.size should be(1)
      }

    }
    "filled" should {
      val matrix = new Matrix[Card](2)
      "give access to its cells" in {
        matrix.card(0, 0) should be(Card(List(Area(corners = List('n'), xy = (0, 0)), Area(corners = List('w'), xy = (0, 0)),
          Area(corners = List('e'), xy = (0, 0)), Area(corners = List('s'), xy = (0, 0)))))
      }
      "returns the number of cards " in {
        matrix.getCount should be (0)
      }
      "replace cells and return a new data structure" in {
        val returnedMatrix = matrix.replaceCell(0, 0, Card(List(Area(corners = List('n'), xy = (0, 0)),
          Area(corners = List('w'), xy = (0, 0)), Area(corners = List('e'), xy = (0, 0)), Area(corners = List('s'), xy = (0, 0)))))
        matrix.card(0, 0) should be(Card(List(Area(corners = List('n'), xy = (0, 0)), Area(corners = List('w'), xy = (0, 0)),
          Area(corners = List('e'), xy = (0, 0)), Area(corners = List('s'), xy = (0, 0)))))
        returnedMatrix.card(0, 0) should be(Card(List(Area(corners = List('n'), xy = (0, 0)),
          Area(corners = List('w'), xy = (0, 0)), Area(corners = List('e'), xy = (0, 0)), Area(corners = List('s'), xy = (0, 0)))))
      }
      "check Edges in Matrix for the n,s,w,e" in {
        matrix.checkEdge(0,1,'n') should be (true)
        matrix.checkEdge(0,0,'n') should be (false)
        matrix.checkEdge(0,0,'s') should be (true)
        matrix.checkEdge(0,2,'s') should be (false)
        matrix.checkEdge(1,0,'w') should be (true)
        matrix.checkEdge(0,0,'w') should be (false)
        matrix.checkEdge(0,0,'e') should be (true)
        matrix.checkEdge(2,0,'e') should be (false)
      }
      "check Env is empty" in {
        matrix.checkEnvEmpty(0,1,'n') should be (true)
        matrix.checkEnvEmpty(0,0,'s') should be (true)
        matrix.checkEnvEmpty(1,0,'w') should be (true)
        matrix.checkEnvEmpty(0,0,'e') should be (true)
      }
      "check Env of a Card" in {
        matrix.checkEnv(0,1,'n', matrix.card(0,1)) should be (true)
        matrix.checkEnv(0,0,'s', matrix.card(0,0)) should be (true)
        matrix.checkEnv(1,0,'w',matrix.card(1,0)) should be (true)
        matrix.checkEnv(0,0,'e',matrix.card(0,0)) should be (true)
      }
       "get Directon on Env" in {
         matrix.getDirEnv(0,0,'n') should be (None)
         matrix.getDirEnv(0,0,'s') should be (None)
         matrix.getDirEnv(1,0,'w') should be (None)
         matrix.getDirEnv(0,0,'e') should be (None)

       }
      "get Neighbor" in {
        matrix.hasNeighbor(0,0) should be (false)
        matrix.hasNeighbor(0,1) should be (false)
      }
      "check if to set okay" in {
        matrix.checkSet(0,1, matrix.card(0,1)) should be (false)
        matrix.checkSet(0,0,matrix.card(0,0)) should be (false)
        matrix.checkSet(1,0,matrix.card(1,0)) should be (false)
      }
    }
    "filled with nonEmpty Card" should {
      val emptyMatrix = new Matrix(3)
      val joinableCard = RawCardFactory("selectCard", 0, 14).drawCard().finalCard(1, 1)
      var fullMatrix = emptyMatrix.replaceCell(0, 0, joinableCard)
      fullMatrix = fullMatrix.replaceCell(0, 1, joinableCard)
      fullMatrix = fullMatrix.replaceCell(0, 2, joinableCard)
      fullMatrix = fullMatrix.replaceCell(1, 0, joinableCard)
      fullMatrix = fullMatrix.replaceCell(1, 1, joinableCard)
      fullMatrix = fullMatrix.replaceCell(1, 2, joinableCard)
      fullMatrix = fullMatrix.replaceCell(2, 0, joinableCard)
      fullMatrix = fullMatrix.replaceCell(2, 1, joinableCard)
      fullMatrix = fullMatrix.replaceCell(2, 2, joinableCard)

      "Check Env is empty" in {
        fullMatrix.checkEnv(1, 1, 'w', joinableCard) should be(true)
        fullMatrix.checkEnv(1, 1, 'e',joinableCard) should be(true)
        fullMatrix.checkEnv(1, 1, 's',joinableCard) should be(true)
        fullMatrix.checkEnv(1, 1, 'n',joinableCard) should be(true)
      }
      "Get directional Area" in {
        fullMatrix.getDirEnv(1, 1, 'n') should be(Some(Area('c', List('s'), -1, (1, 1))))
        fullMatrix.getDirEnv(1, 1, 's') should be(Some(Area('c', List('n'), -1, (1, 1))))
        fullMatrix.getDirEnv(1, 1, 'w') should be(Some(Area('c', List('e'), -1, (1, 1))))
        fullMatrix.getDirEnv(1, 1, 'e') should be(Some(Area('c', List('w'), -1, (1, 1))))
      }
    }
  }

}