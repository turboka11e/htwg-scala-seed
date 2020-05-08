package de.htwg.se.Carcassonne

import de.htwg.se.Carcassonne.model.{Player, Spielfeld}

object Carcassonne {
  def main(args: Array[String]): Unit = {
    val student = Player("Mark Boehme")
    println("Hello, " + student.name)
    val spielfeld = Spielfeld(1)
    print(spielfeld)
  }
}
