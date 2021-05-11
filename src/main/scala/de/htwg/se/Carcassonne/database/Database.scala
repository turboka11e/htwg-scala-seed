package de.htwg.se.Carcassonne.database

import com.google.inject.{Guice, Injector}
import de.htwg.se.Carcassonne.CarcassonneModule
import de.htwg.se.Carcassonne.database.dao.PlayerDaoInterface
import net.codingwell.scalaguice.InjectorExtensions.ScalaInjector

object Database {

  val injector: Injector = Guice.createInjector(new CarcassonneModule)
  val database: DatabaseInterface = injector.instance[DatabaseInterface]

  def playerDao: PlayerDaoInterface = database.getPlayerDao

}
