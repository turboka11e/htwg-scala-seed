package de.htwg.se.Carcassonne.aview.tui

import org.scalatest._

class TUISpec extends WordSpec with Matchers {

  "+++ Blueprint +++" should {
    val test = 1
    "+++ Blueprint +++" in {
      test should be(2)
    }
  }
}
