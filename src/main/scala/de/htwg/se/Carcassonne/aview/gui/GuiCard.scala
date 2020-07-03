package de.htwg.se.Carcassonne.aview.gui

import java.awt.geom.AffineTransform
import java.io.File

import javax.imageio.ImageIO
import java.awt.Graphics2D
import java.awt.image.{AffineTransformOp, BufferedImage}

import de.htwg.se.Carcassonne.controller.controllerComponent.ControllerInterface
import de.htwg.se.Carcassonne.controller.controllerComponent.controllerBaseImpl.{PlayfieldChanged, SetGrid}

import scala.swing.event.MouseClicked
import scala.swing.{Dimension, FlowPanel}

class GuiCard(controller: ControllerInterface, row:Int, col:Int) extends FlowPanel {

  preferredSize = new Dimension(80, 80)
  listenTo(controller, mouse.clicks)

  var img: BufferedImage = findImage()

  override def paint(g:Graphics2D):Unit = {
    g.drawImage(img, 0, 0, null)
    val manican = "./src/main/scala/de/htwg/se/Carcassonne/aview/media/manican"
    val dirCombi = List(('n', 27, 0), ('s', 25, 55), ('w', 0, 27), ('e', 55, 27))
    for {
      x <- controller.getPlayfield.getGrid.card(row, col).getAreas
    } {
      if(x.getPlayer != -1) {
        val dir = x.getCorners.head
        val combi = dirCombi.find(p => p._1.equals(dir)).get
        val manicanPlayer = manican + x.getPlayer + ".png"
        g.drawImage(ImageIO.read(new File(manicanPlayer)), combi._2, combi._3, null)
      }
    }
  }

  def setCard(): Unit = {
    img = findImage()
    for (x <- 0 until controller.getPlayfield.getGrid.card(row, col).getID._2) rotateCardR()
    repaint()
  }

  def findImage(): BufferedImage = {
    val numToCharImage = List("D", "E", "G", "H", "I", "J", "K", "L", "N", "O", "R", "T", "U", "V", "C", "W")

    val index = controller.getPlayfield.getGrid.card(row, col).getID._1

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
    val image = img
    val transform = new AffineTransform
    transform.rotate(Math.PI / 2, image.getWidth / 2, image.getHeight / 2)
    val op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR)
    img = op.filter(image, null)
  }

  def close(): Unit = {
    deafTo(controller)
  }

  reactions += {
    case event: PlayfieldChanged => setCard()
    case event: MouseClicked => controller.placeCard(row, col)
  }

}
