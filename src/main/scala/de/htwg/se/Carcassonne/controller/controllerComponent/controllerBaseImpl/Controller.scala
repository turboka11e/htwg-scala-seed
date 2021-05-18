package de.htwg.se.Carcassonne.controller.controllerComponent.controllerBaseImpl

import com.google.inject.{Guice, Inject, Injector}
import de.htwg.se.Carcassonne.CarcassonneModule
import de.htwg.se.Carcassonne.controller.controllerComponent.GameStatus._
import de.htwg.se.Carcassonne.controller.controllerComponent.{ControllerInterface, GameStatus}
import de.htwg.se.Carcassonne.database.Database
import de.htwg.se.Carcassonne.model.fileComponent.FileInterface
import de.htwg.se.Carcassonne.model.playfieldComponent.PlayfieldInterface
import de.htwg.se.Carcassonne.util.UndoManager
import net.codingwell.scalaguice.InjectorExtensions._


class Controller @Inject()(var playfield: PlayfieldInterface) extends ControllerInterface {

  var gameStatus: GameStatus = IDLE
  private var undoManager = new UndoManager
  val injector: Injector = Guice.createInjector(new CarcassonneModule)
  val fileIo: FileInterface = injector.instance[FileInterface]

  def statusText: String = GameStatus.message(gameStatus)

  def newGame(): Unit = {
    playfield = injector.instance[PlayfieldInterface]
    undoManager = new UndoManager
    gameStatus = NEW
    publish(new SetGrid)
  }

  def createGrid(size: Int): Unit = {
    playfield = playfield.fieldSize(size)
    gameStatus = RESIZE
    publish(new AddPlayers)
  }

  def addPlayer(name: String): Unit = {
    playfield = playfield.addPlayer(name)
    gameStatus = PLAYER
    publish(new AddPlayers)
  }

  def firstCard(): Unit = {
    playfield = playfield.changeGameState(3).getFreshCard
    gameStatus = FIRSTCARD
    publish(new FirstCard)
  }

  def firstCard(select: Int): Unit = {
    playfield = playfield.changeGameState(3).getFreshCard(select)
    gameStatus = FIRSTCARD
    publish(new FirstCard)
  }

  def changeGameState(gs: Int): Unit = {
    playfield = playfield.changeGameState(gs)
    publish(new PlayfieldChanged)
  }

  def getGameState: Int = playfield.gameState

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
    if (playfield.legalPlace(x, y)) {
      undoManager.doStep(new PlaceCommand(x, y, playfield, this))
      gameStatus = PLACE
      publish(new PlayfieldChanged)
    }
  }

  def placeAble(): Unit = {
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

  def save(): Unit = {
    fileIo.save(playfield)
    gameStatus = SAVE
    publish(new PlayfieldChanged)
  }

  def load(): Unit = {
    playfield = fileIo.load
    gameStatus = LOADED
    publish(new PlayfieldChanged)
  }

  def getScore(): String = {
    val score = Database.playerDao.readPlayers().toString()
    println(score)
    score
  }

}
