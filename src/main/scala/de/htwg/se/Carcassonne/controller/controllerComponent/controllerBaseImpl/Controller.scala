package de.htwg.se.Carcassonne.controller.controllerComponent.controllerBaseImpl

import com.google.inject.{Guice, Inject, Injector}
import net.codingwell.scalaguice.InjectorExtensions._
import de.htwg.se.Carcassonne.CarcassonneModule
import de.htwg.se.Carcassonne.controller.controllerComponent.{ControllerInterface, GameStatus}
import de.htwg.se.Carcassonne.controller.controllerComponent.GameStatus._
import de.htwg.se.Carcassonne.model.playfieldComponent.PlayfieldInterface
import de.htwg.se.Carcassonne.util.UndoManager


class Controller @Inject() (var playfield: PlayfieldInterface) extends ControllerInterface {

  var gameStatus: GameStatus = IDLE
  private var undoManager = new UndoManager
  val injector: Injector = Guice.createInjector(new CarcassonneModule)

  def statusText:String = GameStatus.message(gameStatus)

  def newGame(): Unit = {
    playfield = injector.instance[PlayfieldInterface]
    undoManager = new UndoManager
    gameStatus = NEW
    publish(new SetGrid)
  }

  def getPlayfield: PlayfieldInterface = playfield

  def createGrid(size: Int): Unit = {
    playfield = playfield.fieldSize(size)
    gameStatus = RESIZE
    publish(new AddPlayers)
  }

  def addPlayer(name: String): Unit = {
    playfield.getGameState match {
      case 1 => playfield = playfield.addPlayer(name)
        gameStatus = PLAYER
        publish(new AddPlayers)
      case _ =>
    }
  }

  def firstCard(): Unit = {
    playfield = playfield.changeGameState(3).getFreshCard
    gameStatus = FIRSTCARD
    publish(new PlayfieldChanged)
  }

  def firstCard(select: Int): Unit = {
    playfield = playfield.changeGameState(3).getFreshCard(select)
    gameStatus = FIRSTCARD
    publish(new PlayfieldChanged)
  }

  def changeGameState(gs: Int): Unit = {
    playfield = playfield.changeGameState(gs)
  }

  def getGameState: Int = playfield.getGameState

  def rotateRight(): Unit = {
    playfield = playfield.rotateR
    gameStatus = ROTATE
    publish(new PlayfieldChanged)
  }

  def rotateLeft(): Unit = {
    playfield = playfield.rotateL
    gameStatus = ROTATE
    publish(new PlayfieldChanged)
  }

  def selectArea(nr: Int): Unit = {
    playfield = playfield.selectArea(nr)
    gameStatus = MANICAN
    publish(new PlayfieldChanged)
  }

  def placeCard(x: Int, y: Int): Unit = {
    undoManager.doStep(new PlaceCommand(x, y, playfield, this))
    gameStatus = PLACE
    publish(new PlayfieldChanged)
  }

  def placeAble():Unit = {
    if (!playfield.placeable) {
      gameStatus = GAMEOVER
      publish(new GameOver)
    }
  }

  def undo(): Unit = {
    undoManager.undoStep()
    gameStatus = UNDO
    publish(new PlayfieldChanged)
  }

  def redo(): Unit = {
    undoManager.redoStep()
    gameStatus = REDO
    publish(new PlayfieldChanged)
  }


  def playFieldToString: String = playfield.playFieldToString

}
