package de.htwg.se.Carcassonne.model

case class Spielfeld(x: Int, y: Int) {

  /* Aufbau einzelner Block */
  def block():String = " ┌ g ┐\n g   c \n └ r ┘\n"


  def obereZeile():String = " ┌ g ┐" * x

  def mittlZeile():String = " g   c" * x

  def unterZeile():String = " └ r ┘" * x

  def feldgenerator():String = obereZeile() + "\n" + mittlZeile() + "\n" + unterZeile()

}
