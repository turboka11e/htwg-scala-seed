package de.htwg.se.Carcassonne

import de.htwg.se.Carcassonne.model.{Area, Card, Grid, Player}

object Carcassonne {
  def main(args: Array[String]): Unit = {
    val student = Player("Max Mustermann")
    println("Hello, " + student.name)

    val g0 = new Grid(3)
    val toBeInsertedCard1 = Card(List(Area('c', List('n', 's', 'w')), Area('g', List('e'))))
    val toBeInsertedCard2 = Card(List(Area('r', List('w', 'e')), Area('c', List('n')), Area('g', List('s'))))


    val g1 = g0.set(1, 1, toBeInsertedCard1)
    println(g1.printgrid())
    val g2 = g1.set(1, 1, toBeInsertedCard1.rotateRight())
    println(g2.printgrid())
    val g3 = g2.set(1, 0, toBeInsertedCard2)
    println(g3.printgrid())
    val g4 = g3.set(1, 0, toBeInsertedCard2.rotateRight())
    println(g4.printgrid())
  }
}
