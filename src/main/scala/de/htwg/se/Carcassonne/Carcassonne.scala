package de.htwg.se.Carcassonne

import de.htwg.se.Carcassonne.model.{Grid, Player}

object Carcassonne {
  def main(args: Array[String]): Unit = {
    val student = Player("Max Mustermann")
    println("Hello, " + student.name)

    var gamefield = new Grid(3)
    gamefield = gamefield.set(1, 1, 'g', 'c', 'r', 'c')

    print(gamefield.printgrid())

    /*
    println(""" ┌   ┐ ┌   ┐ ┌ g ┐ ┌   ┐ ┌   ┐
              |             c   r
              | └   ┘ └   ┘ └ g ┘ └   ┘ └   ┘
              | ┌   ┐ ┌ g ┐ ┌ g ┐ ┌ g ┐ ┌   ┐
              |       r   c c   r r   c
              | └   ┘ └ r ┘ └ r ┘ └ g ┘ └   ┘
              | ┌   ┐ ┌ r ┐ ┌   ┐ ┌ g ┐ ┌   ┐
              |       r   c       r   c
              | └   ┘ └ r ┘ └   ┘ └ r ┘ └   ┘""".stripMargin)
    */
  }
}
