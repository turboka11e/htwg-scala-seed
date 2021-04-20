package de.htwg.se.Carcassonne

import de.htwg.se.Carcassonne.model.fileIOComponent.RestControllerFileIO

import scala.io.StdIn

object FileIO {

  val restController = RestControllerFileIO

  def main(args: Array[String]): Unit = {

    StdIn.readLine()
  }

}
