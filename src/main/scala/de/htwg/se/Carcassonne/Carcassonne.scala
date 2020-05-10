package de.htwg.se.Carcassonne

import de.htwg.se.Carcassonne.model.{Player, Spielfeld}
import io.StdIn._

object Carcassonne {
  def main(args: Array[String]): Unit = {
    val student = Player("Mark Boehme")
    println("Hello, " + student.name)

    val spielfeld1 = Spielfeld(3, 3)

    // spielfeld1.rmatrix()
    spielfeld1.emptymatrix()
    println(spielfeld1.matrixfeldgenerator())

    while(true) {
      print("Geben Sie eine X-Koord. ein: ")
      val xc = readInt()
      print("Geben Sie eine Y-Koord. ein: ")
      val yc = readInt()
      println()
      spielfeld1.insertKarte(xc, yc)
      println(spielfeld1.matrixfeldgenerator())
    }

    //print(spielfeld1.feldgenerator(" ", " ", " ", " "))




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
