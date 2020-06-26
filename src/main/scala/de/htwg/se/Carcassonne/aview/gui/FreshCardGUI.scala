package de.htwg.se.Carcassonne.aview.gui

import java.io.File

import javax.imageio.ImageIO
import java.awt.{Dimension, Graphics2D}
import java.awt.image.{AffineTransformOp, BufferedImage}
import java.awt.geom.AffineTransform
import java.awt.image.BufferedImage

import de.htwg.se.Carcassonne.controller.{Controller, PlayfieldChanged}

import scala.swing.event.ButtonClicked
import scala.swing.{Button, FlowPanel, GridBagPanel}

class FreshCardGUI(controller: Controller) extends GridBagPanel {

  def constraints(x: Int, y: Int,
                  gridwidth: Int = 1, gridheight: Int = 1,
                  weightx: Double = 0.0, weighty: Double = 0.0,
                  fill: GridBagPanel.Fill.Value = GridBagPanel.Fill.None)
  : Constraints = {
    val c = new Constraints
    c.gridx = x
    c.gridy = y
    c.gridwidth = gridwidth
    c.gridheight = gridheight
    c.weightx = weightx
    c.weighty = weighty
    c.fill = fill
    c
  }

  background = java.awt.Color.BLACK

  val freshCardImage: FlowPanel = new FlowPanel {
    preferredSize = new Dimension(80, 80)

    listenTo(controller)

    var img: BufferedImage = findImage()

    override def paint(g:Graphics2D):Unit = {
      g.drawImage(img, 0, 0, null)
    }

    def setCard(): Unit = {
      img = findImage()
      print(controller.playfield.freshCard.card.getID._2)
      for (x <- 0 until controller.playfield.freshCard.card.getID._2) rotateCardR()
      repaint()
    }

    def findImage(): BufferedImage = {
      val numToCharImage = List("D", "E", "G", "H", "I", "J", "K", "L", "N", "O", "R", "T", "U", "V", "C", "W")

      val index = controller.playfield.freshCard.card.getID._1

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
    }
  }
  val rotateR_Button = new Button("Rechts")
  val rotateL_Button = new Button("Links")

  add(freshCardImage, constraints(0, 0, weighty = 2))
  add(rotateR_Button, constraints(1, 1))
  add(rotateL_Button, constraints(0, 1))

  listenTo(controller, rotateR_Button, rotateL_Button)

  reactions += {
    case ButtonClicked(b) => {
      if(b == rotateL_Button) {
        controller.rotateLeft()
      }
      else if(b == rotateR_Button) {
        controller.rotateRight()
      }
    }
  }
}
