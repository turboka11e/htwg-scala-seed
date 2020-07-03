package de.htwg.se.Carcassonne.aview.gui

import java.awt.event.KeyEvent
import java.io.File

import de.htwg.se.Carcassonne.controller.controllerComponent.ControllerInterface
import de.htwg.se.Carcassonne.controller.controllerComponent.controllerBaseImpl.{AddPlayers, NewGame, SetGrid}
import javax.imageio.ImageIO
import javax.swing.{BorderFactory, ImageIcon}

import scala.swing._
import scala.swing.event.{ButtonClicked, KeyTyped}

class StartGUI(controller: ControllerInterface) extends Frame {

  listenTo(controller)

  title = "Carcassone"
  background = java.awt.Color.WHITE
  preferredSize = new Dimension(1000, 700)

  visible = true
  val gsize: Int = controller.getPlayfield.getGrid.getSize
  var cells: Array[Array[GuiCard]] = Array.ofDim[GuiCard](gsize, gsize)

  val infoLabel: Label = new Label("") {
    font = new Font("Verdana", 1, 20)
  }
  val infoMidLabel: Label = new Label("Choose Player Name:") {
    font = new Font("Verdana", 1, 20)
  }
  val sizes = List("2", "3", "4", "5", "6", "7", "8", "9", "10")
  val selectGrid = new ComboBox(sizes)
  val playerLabel: Label = new Label("") {
    font = new Font("Verdana", 1, 20)
  }
  val textInput: TextField = new TextField("") {
    columns = 10
    listenTo(keys)
    reactions += {
      case KeyTyped(_, c, _, _) =>
        controller.getGameState match {
          case 1 => if (c == KeyEvent.VK_ENTER && text != "") controller.addPlayer(textInput.text)
          case _ =>
        }
    }
  }
  val confirmButton: Button = new Button("") {
    background = java.awt.Color.DARK_GRAY.brighter().brighter()
    foreground = java.awt.Color.BLACK
    focusable = false
    border = BorderFactory.createEmptyBorder(10, 10, 10, 10)
    font = new Font("Verdana", 1, 20)
  }
  val declineButton: Button = new Button("") {
    background = java.awt.Color.DARK_GRAY.brighter().brighter()
    foreground = java.awt.Color.BLACK
    focusable = false
    border = BorderFactory.createEmptyBorder(10, 10, 10, 10)
    font = new Font("Verdana", 1, 20)
  }
  val startButton: Button = new Button("New Game") {
    background = java.awt.Color.DARK_GRAY.brighter().brighter()
    foreground = java.awt.Color.BLACK
    focusable = false
    border = BorderFactory.createEmptyBorder(10, 10, 10, 10)
    font = new Font("Verdana", 1, 20)
  }
  val addButton: Button = new Button("Add Player") {
    background = java.awt.Color.DARK_GRAY.brighter().brighter()
    foreground = java.awt.Color.BLACK
    focusable = false
    border = BorderFactory.createEmptyBorder(10, 10, 10, 10)
    font = new Font("Verdana", 1, 20)
  }

  val banner: Label = new Label() {
    private val logo = ImageIO.read(new File("./src/main/scala/de/htwg/se/Carcassonne/aview/media/CarcassonneText.png"))
    icon = new ImageIcon(logo)
  }

  val infobox: GridBagPanel = new GridBagPanel() {
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

    visible = true
    textInput.visible = false
    selectGrid.visible = false
    confirmButton.visible = false
    declineButton.visible = false
    playerLabel.visible = false
    infoMidLabel.visible = false
    addButton.visible = false

    val twoButton: FlowPanel = new FlowPanel {
      contents += addButton
      contents += declineButton
    }

    add(startButton, constraints(1, 0))

    add(infoLabel, constraints(1, 0, insets = new Insets(0, 0, 20, 0)))
    add(selectGrid, constraints(0, 1, gridwidth = 2, insets = new Insets(0, 0, 20, 0)))
    add(confirmButton, constraints(0, 5, gridwidth = 2, insets = new Insets(0, 0, 0, 0)))

    add(infoMidLabel, constraints(0, 0, insets = new Insets(0, 0, 20, 0)))
    add(textInput, constraints(0, 1, insets = new Insets(0, 0, 20, 0)))
    add(playerLabel, constraints(0, 2, insets = new Insets(0, 0, 20, 0)))
    add(twoButton, constraints(0, 3))
  }

  listenTo(confirmButton, startButton, declineButton, addButton)

  def welcomeScreenAction(): Unit = {
    infoLabel.text = "New Game"
  }

  def gridSizeSelectAction(): Unit = {
    startButton.visible = false
    confirmButton.visible = true
    infoLabel.text = "Choose Field Size"
    selectGrid.visible = true
    confirmButton.text = "Accept"
  }

  def addPlayersAction(): Unit = {
    infoLabel.visible = false
    confirmButton.visible = false
    addButton.visible = true
    infoMidLabel.visible = true
    controller.changeGameState(1)
    playerLabel.visible = true
    playerLabel.text = {
      var tmpString = ""
      for {
        (p, i) <- controller.getPlayfield.getPlayers.zipWithIndex
      } {
        tmpString += p.name + "  "
      }
      tmpString.trim
    }
    selectGrid.visible = false
    declineButton.visible = true
    infoLabel.text = "Insert Player Name:"
    textInput.text = ""
    declineButton.text = "Start Game"
    textInput.visible = true
  }

  def startGame(): Unit = {
    controller.firstCard()
    new SwingGui(controller)
    this.close()
  }

  reactions += {
    case ButtonClicked(b) =>
      if (b == confirmButton) {
        controller.getGameState match {
          case 0 => controller.createGrid(selectGrid.selection.index + 2)
        }
      } else if (b == startButton) {
        controller.newGame()
      }
      else if (b == declineButton) {
        if (controller.getPlayfield.getPlayers.nonEmpty) {
          startGame()
        }
      } else if (b == addButton) {
        controller.getGameState match {
          case 1 => if (textInput.text != "") controller.addPlayer(textInput.text)
        }
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
