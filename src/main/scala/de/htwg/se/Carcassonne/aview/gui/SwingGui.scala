package de.htwg.se.Carcassonne.aview.gui

import java.io.File

import de.htwg.se.Carcassonne.controller._
import javax.imageio.ImageIO
import javax.swing.ImageIcon

import scala.swing._
import scala.swing.event.MouseClicked

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

  var playerInfos:GridBagPanel = new GridBagPanel() {
    background = java.awt.Color.BLACK

    listenTo(controller)

    val playerPoints = new Label()

    add(playerPoints, constraints(0, 0))

    def updatepoints(): Unit = {
      playerPoints.text = controller.playfield.players.head.toString
      playerPoints.foreground = java.awt.Color.GREEN
    }

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

    reactions += {
      case event: InitPlayfield => updatepoints()
      case event: PlayfieldChanged => updatepoints()
    }

  }


  var freshCard:GridBagPanel = new FreshCardGUI(controller)

  var gridPanel: GridBagPanel = new GridBagPanel() {
    background = java.awt.Color.BLACK
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

    for {
      col <- cells.indices
      row <- cells.indices
    } {
      val tmpCard = new GuiCard(controller, row, col)
      cells(row)(col) = tmpCard
      listenTo(tmpCard)
      add(tmpCard, constraints(row, col))
    }

  }

  def playfieldUpdate(): Unit = {
    gridPanel = new GridBagPanel{
      for {
        col <- cells.indices
        row <- cells.indices
      } cells(row)(col).setCard()
    }
  }

  contents = new BorderPanel {
    add(banner, BorderPanel.Position.North)
    add(gridPanel, BorderPanel.Position.Center)
    add(freshCard, BorderPanel.Position.East)
    add(playerInfos, BorderPanel.Position.West)
  }

  centerOnScreen()

  reactions += {
    case event: PlayfieldChanged => playfieldUpdate()
  }


}
