package de.htwg.se.Carcassonne.model.fileComponent

import de.htwg.se.Carcassonne.model.playfieldComponent.PlayfieldInterface

trait FileInterface {

  def load: PlayfieldInterface
  def save(playfield: PlayfieldInterface): Unit

}
