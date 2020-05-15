package de.htwg.se.Carcassonne.model

case class Card(nord:Char = ' ', west:Char = ' ', east:Char = ' ', south:Char = ' '){
  def isSet: Boolean = nord != ' ' && west != ' ' && east != ' ' && south != ' '
}

