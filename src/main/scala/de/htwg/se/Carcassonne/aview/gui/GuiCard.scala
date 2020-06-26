package de.htwg.se.Carcassonne.aview.gui

import java.awt.geom.AffineTransform
import java.io.File

import de.htwg.se.Carcassonne.controller.{Controller, PlayfieldChanged}
import javax.imageio.ImageIO
import java.awt.{Graphics2D, Image}
import java.awt.image.{AffineTransformOp, BufferedImage}

import scala.swing.event.MouseClicked
import scala.swing.{Dimension, FlowPanel, GridBagPanel}

class GuiCard(controller: Controller, row:Int, col:Int) extends FlowPanel {

  preferredSize = new Dimension(80, 80)
  listenTo(controller, mouse.clicks)

  var img: BufferedImage = findImage()

  override def paint(g:Graphics2D):Unit = {
    g.drawImage(img, 0, 0, null)
  }

  def setCard(): Unit = {
    img = findImage()
    for (x <- 0 until controller.playfield.grid.card(row, col).getID._2) rotateCardR()
    repaint()
  }

  def findImage(): BufferedImage = {
    val numToCharImage = List("D", "E", "G", "H", "I", "J", "K", "L", "N", "O", "R", "T", "U", "V", "C", "W")

    val index = controller.playfield.grid.card(row, col).getID._1

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
    transform.rotate(1.5708, image.getWidth / 2, image.getHeight / 2)
    val op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR)
    img = op.filter(image, null)

  }

  reactions += {
    case event: PlayfieldChanged => setCard()
    case event: MouseClicked => controller.placeCard(row, col)
  }


}
