package de.htwg.se.Carcassonne.aview.gui

import java.io.File

import javax.imageio.ImageIO
import java.awt.{Dimension, Graphics2D}
import java.awt.image.{AffineTransformOp, BufferedImage, ImageObserver}
import java.awt.geom.AffineTransform

import de.htwg.se.Carcassonne.controller.{Controller, PlayfieldChanged}

import scala.swing.event.{ButtonClicked, MouseClicked}
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

    listenTo(controller, mouse.clicks)

    var img: BufferedImage = findImage()

    override def paint(g: Graphics2D): Unit = {
      g.drawImage(img, 0, 0, null)
      val manican = "./src/main/scala/de/htwg/se/Carcassonne/aview/media/manican.png"

      val dirCombi = List(('n', 27, 0), ('s', 25, 55), ('w', 0, 27), ('e', 55, 27))

      if (!controller.playfield.freshCard.card.areas.exists(p => p.getPlayer != -1)) {
        for (x <- dirCombi) {
          controller.playfield.freshCard.card.getAreaLookingFrom(x._1).getValue match {
            case 'c' => g.drawImage(ImageIO.read(new File(manican)), x._2, x._3, null)
            case 'r' => g.drawImage(ImageIO.read(new File(manican)), x._2, x._3, null)
            case 'g' =>
          }
        }
      } else {
        val dir = controller.playfield.freshCard.card.areas.find(p => p.getPlayer != -1).get.getCorners.head
        val combi = dirCombi.find(p => p._1.equals(dir)).get
        g.drawImage(ImageIO.read(new File(manican)), combi._2, combi._3, null)
      }
    }

    def mouseClick(x: Int, y: Int): Unit = {
      if (validateCoord(0, 20, 20, 60)) {
        selectArea('w')
      } else if (validateCoord(20, 60, 0, 20)) {
        selectArea('n')
      } else if (validateCoord(60, 80, 20, 60)) {
        selectArea('e')
      } else if (validateCoord(20, 60, 60, 80)) {
        selectArea('s')
      }

      repaint()

      def validateCoord(xMin: Int, xMax: Int, yMin: Int, yMax: Int): Boolean = xMin < x && x < xMax && yMin < y && y < yMax

      def selectArea(dir: Char): Unit = {
        val area = controller.playfield.freshCard.card.getAreaLookingFrom(dir)
        val index = controller.playfield.freshCard.card.areas.indexWhere(p => p.eq(area))
        controller.selectArea(index)
      }
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
      case MouseClicked(_, p, _, _, _) => mouseClick(p.x, p.y)
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
      if (b == rotateL_Button) {
        controller.rotateLeft()
      }
      else if (b == rotateR_Button) {
        controller.rotateRight()
      }
    }
  }
}
