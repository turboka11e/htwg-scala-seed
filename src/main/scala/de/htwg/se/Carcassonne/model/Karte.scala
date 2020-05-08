package de.htwg.se.Carcassonne.model

case class Karte(nr: Int){

  val rechtsListe: Array[String] = Array(" ", "g", "w")
  val linksListe: Array[String] = Array(" ", "b", "c")
  val obenListe: Array[String] = Array(" ", "b", "c")
  val untenListe: Array[String] = Array(" ", "b", "c")

  val rechts: String = rechtsListe(nr)
  val links: String = linksListe(nr)
  val oben: String = obenListe(nr)
  val unten: String = untenListe(nr)

}
