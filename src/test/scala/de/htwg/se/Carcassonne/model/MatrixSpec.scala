package de.htwg.se.Carcassonne.model

import org.scalatest._

class MatrixSpec extends WordSpec with Matchers {
  "A Matrix is a tailor-made immutable data type that contains a two-dimentional Vector of Cells. A Matrix" when {
    "empty " should {
      "be created by using a dimention and a sample cell" in {
        val matrix = new Matrix[Card](2)
        matrix.size should be(2)
      }
      "for test purposes only be created with a Vector of Vectors" in {
        val testMatrix = Matrix(Vector(Vector(Card())))
        testMatrix.size should be(1)
      }

    }
    "filled" should {
      val matrix = new Matrix[Card](2)
      "give access to its cells" in {
        matrix.card(0, 0) should be(Card(List(Area(corners = List('n'), xy = (0, 0)), Area(corners = List('w'), xy = (0, 0)),
          Area(corners = List('e'), xy = (0, 0)), Area(corners = List('s'), xy = (0, 0)))))
      }
      "replace cells and return a new data structure" in {
        val returnedMatrix = matrix.replaceCell(0, 0, Card(List(Area(corners = List('n'), xy = (0, 0)),
          Area(corners = List('w'), xy = (0, 0)), Area(corners = List('e'), xy = (0, 0)), Area(corners = List('s'), xy = (0, 0)))))
        matrix.card(0, 0) should be(Card(List(Area(corners = List('n'), xy = (0, 0)), Area(corners = List('w'), xy = (0, 0)),
          Area(corners = List('e'), xy = (0, 0)), Area(corners = List('s'), xy = (0, 0)))))
        returnedMatrix.card(0, 0) should be(Card(List(Area(corners = List('n'), xy = (0, 0)),
          Area(corners = List('w'), xy = (0, 0)), Area(corners = List('e'), xy = (0, 0)), Area(corners = List('s'), xy = (0, 0)))))
      }
    }
  }

}