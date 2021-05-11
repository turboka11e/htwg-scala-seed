package de.htwg.se.Carcassonne.database

import de.htwg.se.Carcassonne.database.dao.PlayerDaoInterface

trait DatabaseInterface {

  def getPlayerDao: PlayerDaoInterface

}
