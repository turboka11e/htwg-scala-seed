package de.htwg.se.Carcassonne.model.playerComponent

import org.scalatest._

class PlayerSpec extends WordSpec with Matchers {
  "A Player" when {
    "new" should {
      val player = Player("Your Name")
      "have a name" in {
        player.name should be("Your Name")
      }
      "have a nice String representation" in {
        val name = "Your Name"
        val points = 0.0
        player.toString should be(f"$name ($points%.2f)")
      }
    }
  }


}
