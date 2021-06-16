package de.htwg.se.Carcassonne.aview.tui
import de.htwg.se.Carcassonne.controller.GameState

trait PrettyPrintStrategyTemplate {

  def printo(gameState:GameState): String

}
