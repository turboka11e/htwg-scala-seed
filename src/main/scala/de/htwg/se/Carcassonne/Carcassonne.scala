package de.htwg.se.Carcassonne

import de.htwg.se.Carcassonne.model.{Area, Card, Grid, Player}

object Carcassonne {
  def main(args: Array[String]): Unit = {
    val student = Player("Max Mustermann")
    println("Hello, " + student.name)

    val emptyGrid = new Grid(3)
    val oneCard = Card(List(Area('r', List('w', 'e'), Player("Lukas")), Area('c', List('n')), Area('g', List('s'))))
    val twoCard = Card(List(Area('r', List('w', 'e'), Player("Mark")), Area('c', List('n')), Area('g', List('s'))))
    val threeCard = Card(List(Area('r', List('w', 'e')), Area('c', List('n')), Area('c', List('s'), Player("Adam"))))
    val oneCardGrid = emptyGrid.place(1, 1, oneCard)
    val twoCardGrid = oneCardGrid.place(0, 1, twoCard)
    val threeCardGrid = twoCardGrid.place(1, 0, threeCard)

    println(threeCardGrid.toString)
    println(threeCardGrid.summonTerritories.toString)

  }
}
