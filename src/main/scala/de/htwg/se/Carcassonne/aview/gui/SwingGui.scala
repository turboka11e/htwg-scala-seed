package de.htwg.se.Carcassonne.aview.gui

import java.io.File

import de.htwg.se.Carcassonne.controller._
import javax.imageio.ImageIO
import javax.swing.ImageIcon

import scala.swing._

class SwingGui(controller: Controller) extends Frame {

  listenTo(controller)

  title = "Carcassone"
  preferredSize = new Dimension(1000, 700)

  /* Cells is Array for GuiCard Classes */
  val gsize: Int = controller.playfield.grid.size
  var cells: Array[Array[GuiCard]] = Array.ofDim[GuiCard](gsize, gsize)

  /* Components for main GUI */
  val banner: Label = new Label() {
    private val logo = ImageIO.read(new File("./src/main/scala/de/htwg/se/Carcassonne/aview/media/CarcassonneText.png"))
    icon = new ImageIcon(logo)
  }
  val controlPanel: BorderPanel = new BorderPanel {

    val playerInfos: GridBagPanel = new GridBagPanel() {

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

      background = java.awt.Color.LIGHT_GRAY

      listenTo(controller)

      val playerLables: List[Label] = {
        var tmpLables: List[Label] = Nil
        for {
          p <- controller.playfield.players
        } {
          val tmpPlayer = new Label() {
            text = p.toString
            font = new Font("Verdana", 1, 30)
          }
          tmpLables = tmpPlayer :: tmpLables
        }
        tmpLables.reverse
      }

      for {
        i <- controller.playfield.players.indices
      } {
        add(playerLables(i), constraints(0, i, insets = new Insets(5, 0, 5, 0)))
      }

      def updatepoints(): Unit = {
        for {
          i <- controller.playfield.players.indices
        } {
          playerLables(i).text = controller.playfield.players(i).toString
        }
      }

      reactions += {
        case event: PlayfieldChanged => updatepoints()
      }

    }

    add(playerInfos, BorderPanel.Position.North)
    add(new FreshCardGUI(controller), BorderPanel.Position.Center)
  }
  val gridPanel: GridBagPanel = new GridBagPanel() {
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

  contents = new BorderPanel {
    add(banner, BorderPanel.Position.North)
    add(gridPanel, BorderPanel.Position.Center)
    add(controlPanel, BorderPanel.Position.East)
  }
  centerOnScreen()
  visible = true
}
