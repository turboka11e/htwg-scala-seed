package de.htwg.se.Carcassonne.model

case class Player(name: String, points:Double = 0) {
   override def toString:String = name

}

