package de.htwg.se.Carcassonne.aview.gui

import java.io.File

import de.htwg.se.Carcassonne.controller._
import javax.imageio.ImageIO
import javax.swing.ImageIcon

import scala.swing.BorderPanel.Position
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

  val addPlayersPanel: FlowPanel = new FlowPanel {
    visible = true
    contents += new Button("Hier könnte dein Name stehen")
  }

  val gridPanel: GridPanel = new GridPanel(gsize, gsize) {
    visible = false
  }

  def welcomeScreenAction(): Unit = {

  }

  def gridSizeSelectAction(): Unit = {

  }

  def addPlayersAction(): Unit = {

  }




  def playfieldAction(): Unit = gridPanel.visible = true


  contents = new BorderPanel {
    add(banner, BorderPanel.Position.North)
  }

  centerOnScreen()

  reactions += {
    case event: NewGame => welcomeScreenAction()
    case event: SetGrid => gridSizeSelectAction()
    case event: AddPlayers => addPlayersAction()
    case event: InitPlayfield => playfieldAction()
  }


}
