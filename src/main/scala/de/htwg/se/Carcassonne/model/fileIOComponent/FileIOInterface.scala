package de.htwg.se.Carcassonne.model.fileIOComponent

import de.htwg.se.Carcassonne.model.playfieldComponent.PlayfieldInterface

trait FileIOInterface {

  def load: PlayfieldInterface
  def save(playfieldInterface: PlayfieldInterface): PlayfieldInterface

}
