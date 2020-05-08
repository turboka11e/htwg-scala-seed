package de.htwg.se.Carcassonne

import de.htwg.se.Carcassonne.model.{Player, Spielfeld}

object Carcassonne {
  def main(args: Array[String]): Unit = {
    val student = Player("Mark Boehme")
    println("Hello, " + student.name)

    print(Spielfeld(2,3).feldgenerator())
  }
}
