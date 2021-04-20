package de.htwg.se.Carcassonne.model.fileIOComponent

trait FileIOInterface {

  def load(): String
  def save(playfield: String): Unit

}
