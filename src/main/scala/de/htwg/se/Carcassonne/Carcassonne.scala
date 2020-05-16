package de.htwg.se.Carcassonne

import de.htwg.se.Carcassonne.model.{Area, Grid, Player}

object Carcassonne {
  def main(args: Array[String]): Unit = {
    val student = Player("Max Mustermann")
    println("Hello, " + student.name)

    val g0 = new Grid(3)

    val g1 = g0.set(1, 1, List(Area('c', List('n', 's', 'w')), Area('g', List('e'))))

    print(g1.printgrid())
  }
}
