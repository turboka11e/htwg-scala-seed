package de.htwg.se.Carcassonne.controller.controllerRestImpl

import scala.swing.event.Event

abstract class PlayfieldEvent(field: String) extends Event {
  def playfield(): String = {
    field
  }
}

class PlayfieldChanged(playfield: String) extends PlayfieldEvent(playfield)

class SetGrid(playfield: String) extends PlayfieldEvent(playfield)

class AddPlayers(playfield: String) extends PlayfieldEvent(playfield)

class FirstCard(playfield: String) extends PlayfieldEvent(playfield)

class GameOverTUI extends Event

class InvalidInputString extends Event