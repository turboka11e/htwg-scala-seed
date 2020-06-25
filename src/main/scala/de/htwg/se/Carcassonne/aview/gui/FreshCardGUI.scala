package de.htwg.se.Carcassonne.aview.gui

import java.io.File

import de.htwg.se.Carcassonne.model.{Area, Card, CardManipulator}
import javax.imageio.ImageIO
import java.awt.Graphics2D
import java.awt.image.BufferedImage

import de.htwg.se.Carcassonne.controller.RotateR

import scala.swing.{GridPanel, Label, Panel}

class FreshCardGUI(card:CardManipulator) extends Panel {

  var img: BufferedImage = findImage()

  override def paint(g:Graphics2D):Unit = {
    g.drawImage(img, 0, 0, null)
  }

  def setCard(): Unit = {
    img = findImage()
    repaint()
  }

  def findImage(): BufferedImage = {
    val numToCharImage = List("D", "E", "G", "H", "I", "J", "K", "L", "N", "O", "R", "T", "U", "V", "W")

    val index = card.card.getID

    var dataImg: String = ""
    if (index == -1) {
      dataImg = "Empty"
    } else {
      dataImg = numToCharImage(index)
    }
    val src = "./src/main/scala/de/htwg/se/Carcassonne/aview/media/" + dataImg + ".png"
    ImageIO.read(new File(src))
  }

  def rotateCardR(): Unit = {
    img.createGraphics().rotate(Math.PI / 2)
    repaint()
  }

  reactions += {
    case event: RotateR => rotateCardR()
  }
}
