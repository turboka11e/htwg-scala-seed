package de.htwg.se.Carcassonne.model.playerComponent

case class Player(name: String, points:Double = 0) {

   override def toString:String = f"$name ($points%.2f)"

}
