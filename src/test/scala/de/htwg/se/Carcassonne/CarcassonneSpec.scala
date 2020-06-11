package de.htwg.se.Carcassonne

import org.scalatest.{Matchers, WordSpec}

class CarcassonneSpec extends WordSpec with Matchers {

  "The Carcassonne main class" should {
    "accept text input as argument without readline loop, to test it from command line " in {
      Carcassonne.main(Array[String]("q"))
    }
  }

}