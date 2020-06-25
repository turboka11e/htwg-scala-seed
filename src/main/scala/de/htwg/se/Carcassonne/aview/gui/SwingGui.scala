package de.htwg.se.Carcassonne.aview.gui

import java.io.File

import de.htwg.se.Carcassonne.controller._
import javax.imageio.ImageIO
import javax.swing.ImageIcon

import scala.swing._

class SwingGui(controller: Controller) extends Frame {

  listenTo(controller)

  title = "Carcassone"
  background = java.awt.Color.WHITE
  preferredSize = new Dimension(1000, 700)
  //resizable = false

  visible = true
  val gsize: Int = controller.playfield.grid.size
  var cells: Array[Array[GuiCard]] = Array.ofDim[GuiCard](gsize, gsize)

  val banner: Label = new Label() {
    private val logo = ImageIO.read(new File("./src/main/scala/de/htwg/se/Carcassonne/aview/media/CarcassonneText.png"))
    icon = new ImageIcon(logo)
  }

  var gridPanel: GridPanel = new GridPanel(gsize, gsize) {

    for {
      line <- cells.indices
      row <- cells.indices
    } {
      val tmpCard = new GuiCard(controller, line, row)
      cells(line)(row) = tmpCard
      listenTo(tmpCard)
      contents += tmpCard
    }
  }

  def playfieldUpdate(): Unit = {
    gridPanel = new GridPanel(gsize, gsize){
      for {
        line <- cells.indices
        row <- cells.indices
      } cells(line)(row).setCard()
    }
  }


  contents = new BorderPanel {
    add(banner, BorderPanel.Position.North)
    add(gridPanel, BorderPanel.Position.Center)
  }

  centerOnScreen()

  reactions += {
    case event: PlayfieldChanged => playfieldUpdate()
  }


}
