package de.htwg.se.Carcassonne

import de.htwg.se.Carcassonne.model.{Player, Spielfeld}

object Carcassonne {
  def main(args: Array[String]): Unit = {
    val student = Player("Mark Boehme")
    println("Hello, " + student.name)

    //print(Spielfeld(2,2).feldgenerator())

    //print(Spielfeld(5,5).feldgenerator("g", "r", "c", "r"))

    println(""" ┌   ┐ ┌   ┐ ┌ g ┐ ┌   ┐ ┌   ┐
              |             c   r
              | └   ┘ └   ┘ └ g ┘ └   ┘ └   ┘
              | ┌   ┐ ┌ g ┐ ┌ g ┐ ┌ g ┐ ┌   ┐
              |       r   c c   r r   c
              | └   ┘ └ r ┘ └ r ┘ └ g ┘ └   ┘
              | ┌   ┐ ┌ r ┐ ┌ r ┐ ┌ g ┐ ┌   ┐
              |       r   c c   r r   c
              | └   ┘ └ r ┘ └ c ┘ └ r ┘ └   ┘""".stripMargin)

  }
}
