package de.htwg.se.Carcassonne.model

case class Spielfeld(x: Int, y: Int) {

  val oben: String = Karte(0).oben
  val links: String = Karte(0). links
  val rechts: String = Karte(0).rechts
  val unten: String = Karte(0).unten

  /* Aufbau einzelner Block */
  def block():String = s" ┌ g ┐\n g   c \n └ r ┘\n"

  def obereZeile():String = s" ┌ $oben ┐" * x

  def mittlZeile():String = s" $links   $rechts" * x

  def unterZeile():String = s" └ $unten ┘" * x

  def reihengenerator():String = obereZeile() + "\n" + mittlZeile() + "\n" + unterZeile()

  def feldgenerator():String = (reihengenerator() + "\n") * y

}
