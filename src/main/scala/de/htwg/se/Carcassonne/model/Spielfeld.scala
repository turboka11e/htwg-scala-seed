package de.htwg.se.Carcassonne.model

case class Spielfeld(size: Int) {
  def aufbau():String = " ┌ g ┐\n g   c \n └ r ┘\n" * size
}
