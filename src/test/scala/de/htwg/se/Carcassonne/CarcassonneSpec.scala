package de.htwg.se.Carcassonne

import org.scalatest.{Matchers, WordSpec}

class CarcassonneSpec extends WordSpec with Matchers {

  "The Sudoku main class" should {
    "accept text input as argument without readline loop, to test it from command line " in {
      Carcassonne.main(Array[String]("q"))
    }
  }

}