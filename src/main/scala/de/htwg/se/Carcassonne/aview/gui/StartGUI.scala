package de.htwg.se.Carcassonne.aview.gui

import java.io.File

import de.htwg.se.Carcassonne.controller._
import javax.imageio.ImageIO
import javax.swing.ImageIcon

import scala.swing.BorderPanel.Position
import scala.swing._
import scala.swing.event.ButtonClicked

class StartGUI(controller: Controller) extends Frame {

  listenTo(controller)

  title = "Carcassone"
  background = java.awt.Color.WHITE
  preferredSize = new Dimension(1000, 700)
  //resizable = false

  visible = true
  val gsize: Int = controller.playfield.grid.size
  var cells: Array[Array[GuiCard]] = Array.ofDim[GuiCard](gsize, gsize)
  val infoLabel = new Label("")
  val sizes = List("2", "3", "4", "5", "6", "7", "8", "9", "10")
  val selectGrid = new ComboBox(sizes)
  val playerLabel = new Label("")
  val textInput: TextField = new TextField("") {
    columns = 10
  }
  val confirmButton = new Button("")
  val declineButton = new Button("")
  val startButton = new Button("Los gehts!")

  val banner: Label = new Label() {
    private val logo = ImageIO.read(new File("./src/main/scala/de/htwg/se/Carcassonne/aview/media/CarcassonneText.png"))
    icon = new ImageIcon(logo)
  }

  val infobox: GridBagPanel = new GridBagPanel() {
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

    visible = true
    textInput.visible = false
    selectGrid.visible = false
    confirmButton.visible = false
    declineButton.visible = false
    playerLabel.visible = false
    add(infoLabel, constraints(0, 0))
    add(selectGrid, constraints(0, 1))
    add(textInput, constraints(0, 2))
    add(confirmButton, constraints(0, 3))
    add(startButton, constraints(0, 4))
    add(playerLabel, constraints(0, 5))
    add(declineButton, constraints(0, 6))

  }

  listenTo(confirmButton, startButton, declineButton)

  def welcomeScreenAction(): Unit = {
    infoLabel.text = "Neues Spiel?"
  }

  def gridSizeSelectAction(): Unit = {
    startButton.visible = false
    confirmButton.visible = true
    infoLabel.text = "Wähle Feldgröße!"
    selectGrid.visible = true
    confirmButton.text = "Jawoll! Perfekt."
  }

  def addPlayersAction(): Unit = {
    controller.changeGameState(1)
    playerLabel.visible = true
    playerLabel.text = controller.playfield.players.toString()
    selectGrid.visible = false
    declineButton.visible = true
    infoLabel.text = "Spielername eingeben:"
    textInput.text = ""
    confirmButton.text = "Spieler hinzügen!"
    declineButton.text = "Spiel Anfangen!"
    textInput.visible = true
  }

  def startGame(): Unit = {
    controller.firstCard()
    val gui = new SwingGui(controller)
    this.visible = false
  }

  reactions += {
    case ButtonClicked(b) =>
      if (b == confirmButton) {
        controller.getGameState match {
          case 0 => controller.createGrid(selectGrid.selection.index + 2)
          case 1 => controller.addPlayer(textInput.text)
        }
      } else if (b == startButton) {
        controller.newGame()
      }
      else if (b == declineButton) {
        startGame()
      }
  }

  contents = new BorderPanel {
    add(banner, BorderPanel.Position.North)
    add(infobox, BorderPanel.Position.Center)
  }

  centerOnScreen()

  reactions += {
    case event: NewGame => welcomeScreenAction()
    case event: SetGrid => gridSizeSelectAction()
    case event: AddPlayers => addPlayersAction()
  }


}
