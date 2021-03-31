package de.htwg.se.Carcassonne.aview.gui

import java.io.File

import javax.imageio.ImageIO
import java.awt.{Dimension, Graphics2D}
import java.awt.image.{AffineTransformOp, BufferedImage}
import java.awt.geom.AffineTransform

import de.htwg.se.Carcassonne.controller.controllerComponent.ControllerInterface
import de.htwg.se.Carcassonne.controller.controllerComponent.controllerBaseImpl.{Controller, PlayfieldChanged}
import javax.swing.ImageIcon

import scala.swing.event.MouseClicked
import scala.swing.{FlowPanel, GridBagPanel, Insets, Label}

class FreshCardGUI(controller: ControllerInterface) extends GridBagPanel {

  def constraints(x: Int, y: Int,
                  gridwidth: Int = 1, gridheight: Int = 1,
                  weightx: Double = 0.0, weighty: Double = 0.0,
                  fill: GridBagPanel.Fill.Value = GridBagPanel.Fill.None,
                  insets: Insets = new Insets(0, 0, 0, 0))
  : Constraints = {
    val c = new Constraints
    c.gridx = x
    c.gridy = y
    c.gridwidth = gridwidth
    c.gridheight = gridheight
    c.weightx = weightx
    c.weighty = weighty
    c.fill = fill
    c.insets = insets
    c
  }

  background = java.awt.Color.DARK_GRAY

  val freshCardImage: FlowPanel = new FlowPanel {
    preferredSize = new Dimension(80, 80)
    listenTo(controller, mouse.clicks)

    var img: BufferedImage = findImage()

    override def paint(g: Graphics2D): Unit = {
      g.drawImage(img, 0, 0, null)
      val manican = "./src/main/scala/de/htwg/se/Carcassonne/aview/media/manican"
      val freshCardAreas = controller.getPlayfield.freshCard.card.areas

      val dirCombi = List(('n', 27, 0), ('s', 25, 55), ('w', 0, 27), ('e', 55, 27))

      if (!freshCardAreas.exists(p => p.player != -1)) {
        val emptyManican = manican + "Empty.png"
        for (x <- freshCardAreas.indices) {
            val selectDir = dirCombi.indexWhere(p => p._1 == freshCardAreas(x).corners.head)
          //controller.playfield.freshCard.card.getAreaLookingFrom(x._1).getValue
            freshCardAreas(x).value match {
            case 'c' => g.drawImage(ImageIO.read(new File(emptyManican)), dirCombi(selectDir)._2, dirCombi(selectDir)._3, null)
            case 'r' => g.drawImage(ImageIO.read(new File(emptyManican)), dirCombi(selectDir)._2, dirCombi(selectDir)._3, null)
            case 'g' =>
          }
        }
      } else {
        val dir = controller.getPlayfield.freshCard.card.areas.find(p => p.player != -1).get.corners.head
        val combi = dirCombi.find(p => p._1.equals(dir)).get
        val playerManican = manican + controller.getPlayfield.isOn + ".png"
        g.drawImage(ImageIO.read(new File(playerManican)), combi._2, combi._3, null)
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
        val area = controller.getPlayfield.freshCard.card.getAreaLookingFrom(dir)
        val index = controller.getPlayfield.freshCard.card.areas.indexWhere(p => p.eq(area))
        controller.selectArea(index)
      }
    }


    def setCard(): Unit = {
      img = findImage()
      for (x <- 0 until controller.getPlayfield.freshCard.card.id._2) rotateCardR()
      repaint()
    }

    def findImage(): BufferedImage = {
      val numToCharImage = List("D", "E", "G", "H", "I", "J", "K", "L", "N", "O", "R", "T", "U", "V", "C", "W")

      val index = controller.getPlayfield.freshCard.card.id._1

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

    reactions += {
      case event: PlayfieldChanged => setCard()
      case MouseClicked(_, p, _, _, _) => mouseClick(p.x, p.y)
    }
  }
  val rotateRightImage: Label = new Label() {
    listenTo(controller, mouse.clicks)
    icon = new ImageIcon(ImageIO.read(new File("./src/main/scala/de/htwg/se/Carcassonne/aview/media/rotateRight.png")))
    reactions += {
      case event:MouseClicked => controller.rotateRight()
    }
  }
  val rotateLeftImage: Label = new Label() {
    xLayoutAlignment = 10
    listenTo(controller, mouse.clicks)
    icon = new ImageIcon(ImageIO.read(new File("./src/main/scala/de/htwg/se/Carcassonne/aview/media/rotateLeft.png")))
    reactions += {
      case event:MouseClicked => controller.rotateLeft()
    }
  }

  add(freshCardImage, constraints(1, 0, insets = new Insets(0, 10, 0, 10)))
  add(rotateRightImage, constraints(2, 0, insets = new Insets(0, 0, 0, 20)))
  add(rotateLeftImage, constraints(0, 0, insets = new Insets(0, 20, 0, 0)))

  listenTo(controller)

}
