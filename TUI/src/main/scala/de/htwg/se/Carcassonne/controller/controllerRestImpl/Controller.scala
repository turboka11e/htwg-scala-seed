package de.htwg.se.Carcassonne.controller.controllerRestImpl

import de.htwg.se.Carcassonne.controller.{ControllerInterface, RestControllerTUI}

class Controller extends ControllerInterface {

  def controller: RestControllerTUI.type = RestControllerTUI

  override def newGame(): Unit = controller.sendGET_noParam("newGame")

  override def createGrid(size: Int): Unit = controller.sendGET_withParam("createGrid", size.toString)

  override def addPlayer(name: String): Unit = controller.sendGET_withParam("addPlayer", name)

  override def firstCard(): Unit = controller.sendGET_noParam("firstCard")

  override def firstCard(select: Int): Unit = controller.sendGET_withParam("firstCard", select.toString)

  override def changeGameState(gs: Int): Unit = controller.sendGET_withParam("changeGameState", gs.toString)

  override def getGameState: Int = controller.sendAndGET_noParam("getGameState")

  override def rotateRight(): Unit = controller.sendGET_noParam("rotateRight")

  override def rotateLeft(): Unit = controller.sendGET_noParam("rotateLeft")

  override def selectArea(nr: Int): Unit = controller.sendGET_withParam("selectArea", nr.toString)

  override def placeCard(x: Int, y: Int): Unit = controller.sendGET_withQuery("placeCard", x, y)

  override def placeAble(): Unit = controller.sendGET_noParam("placeAble")

  override def undo(): Unit = controller.sendGET_noParam("undo")

  override def redo(): Unit = controller.sendGET_noParam("redo")

  override def save(): Unit = controller.sendGET_noParam("save")

  override def load(): Unit = controller.sendGET_noParam("load")
}
