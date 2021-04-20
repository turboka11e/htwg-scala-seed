package de.htwg.se.Carcassonne.aview.gui

import java.awt.Color
import java.io.File

import de.htwg.se.Carcassonne.controller.controllerComponent.ControllerInterface
import de.htwg.se.Carcassonne.controller.controllerComponent.controllerBaseImpl.{GameOver, PlayfieldChanged}
import javax.imageio.ImageIO
import javax.swing.{BorderFactory, ImageIcon}

import scala.swing._
import scala.swing.event.ButtonClicked

class SwingGui(controller: ControllerInterface) extends Frame {

  listenTo(controller)

  title = "Carcassone"
  preferredSize = new Dimension(1200, 900)

  /* Cells is Array for GuiCard Classes */
  val gsize: Int = controller.playfield.grid.size
  var cells: Array[Array[GuiCard]] = Array.ofDim[GuiCard](gsize, gsize)
  var playerInfos: GridBagPanel = _

  /* Components for main GUI */
  val banner: Label = new Label() {
    private val logo = ImageIO.read(new File("./src/main/scala/de/htwg/se/Carcassonne/aview/media/CarcassonneText.png"))
    icon = new ImageIcon(logo)
  }
  val controlPanel: BorderPanel = new BorderPanel {

    playerInfos = new GridBagPanel() {

      def constraints(x: Int, y: Int,
                      gridwidth: Int = 1, gridheight: Int = 1,
                      weightx: Double = 0.0, weighty: Double = 0.0,
                      fill: GridBagPanel.Fill.Value = GridBagPanel.Fill.None,
                      insets: Insets = new Insets(0, 0, 0, 0),
                      anchor: GridBagPanel.Anchor.Value = GridBagPanel.Anchor.West)
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
        c.anchor = anchor
        c
      }

      background = java.awt.Color.LIGHT_GRAY

      listenTo(controller)

      val playerLables: List[Label] = {
        val manican = "./src/main/scala/de/htwg/se/Carcassonne/aview/media/manican"
        var tmpLables: List[Label] = Nil
        for {
          (p, i) <- controller.playfield.players.zipWithIndex
        } {
          val tmpPlayer = new Label() {
            icon = new ImageIcon(ImageIO.read(new File(manican + i + ".png")))
            foreground = java.awt.Color.BLACK
            text = p.name
            font = new Font("Verdana", 1, 30)
          }
          tmpLables = tmpPlayer :: tmpLables
        }
        tmpLables = tmpLables.reverse
        tmpLables.head.foreground = Color.BLUE
        tmpLables
      }

      val pointsLables: List[Label] = {
        var tmpLables: List[Label] = Nil
        for {
          p <- controller.playfield.players
        } {
          val tmpPlayer = new Label() {
            text = f"${p.points}%.2f"
            font = new Font("Verdana", 1, 30)
          }
          tmpLables = tmpPlayer :: tmpLables
        }
        tmpLables.reverse
      }

      for {
        i <- controller.playfield.players.indices
      } {
        add(playerLables(i), constraints(0, i, gridwidth = 2, insets = new Insets(5, 5, 5, 5), anchor = GridBagPanel.Anchor.West))
        add(pointsLables(i), constraints(2, i, insets = new Insets(5, 5, 5, 5), anchor = GridBagPanel.Anchor.East))
      }

      def updatepoints(): Unit = {
        for {
          i <- controller.playfield.players.indices
        } {
          val points = controller.playfield.players(i).points
          pointsLables(i).text = f"$points%.2f"
          val colors: List[java.awt.Color] = List(Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN)
          if (i == controller.playfield.isOn) {
            playerLables(i).foreground = colors(i)
          } else {
            playerLables(i).foreground = java.awt.Color.BLACK
          }
        }
      }

      reactions += {
        case event: PlayfieldChanged => updatepoints()
      }

    }

    val gameControl: BorderPanel = new BorderPanel {

      val topButtons: BorderPanel = new BorderPanel {

        val newExitButtons: FlowPanel = new FlowPanel {
          background = java.awt.Color.DARK_GRAY
          val newGameButton: Button = new Button("New Game") {
            preferredSize = new Dimension(140, 45)
            background = java.awt.Color.DARK_GRAY.brighter().brighter()
            foreground = java.awt.Color.BLACK
            focusable = false
            border = BorderFactory.createEmptyBorder(10, 10, 10, 10)
            font = new Font("Verdana", 1, 20)

            selected = false
            reactions += {
              case event: ButtonClicked =>
                controller.newGame()
                for {
                  x <- cells.indices
                  y <- cells.indices
                } {
                  cells(x)(y).deafTo(controller)
                }
                playerInfos.deafTo(controller)
                this.deafTo(controller)
                new StartGUI(controller)
                close()
            }
          }
          val exitButton: Button = new Button("Exit Game") {
            preferredSize = new Dimension(140, 45)
            background = java.awt.Color.DARK_GRAY.brighter().brighter()
            foreground = java.awt.Color.BLACK
            focusable = false
            border = BorderFactory.createEmptyBorder(10, 10, 10, 10)
            font = new Font("Verdana", 1, 20)
            reactions += {
              case event: ButtonClicked => sys.exit()
            }
          }
          contents += newGameButton
          contents += exitButton
        }
        val saveLoadButtons: FlowPanel = new FlowPanel {
          background = java.awt.Color.DARK_GRAY
          val saveButton: Button = new Button("Save") {
            preferredSize = new Dimension(140, 45)
            background = java.awt.Color.DARK_GRAY.brighter().brighter()
            foreground = java.awt.Color.BLACK
            focusable = false
            border = BorderFactory.createEmptyBorder(10, 10, 10, 10)
            font = new Font("Verdana", 1, 20)
            reactions += {
              case event: ButtonClicked => controller.save()
            }
          }
          val loadButton: Button = new Button("Load") {
            preferredSize = new Dimension(140, 45)
            background = java.awt.Color.DARK_GRAY.brighter().brighter()
            foreground = java.awt.Color.BLACK
            focusable = false
            border = BorderFactory.createEmptyBorder(10, 10, 10, 10)
            font = new Font("Verdana", 1, 20)
            reactions += {
              case event: ButtonClicked => loadRequest()
            }
          }

          def loadRequest(): Unit = {
            close()
            for {
              x <- cells.indices
              y <- cells.indices
            } {
              cells(x)(y).deafTo(controller)
            }
            playerInfos.deafTo(controller)
            this.deafTo(controller)
            controller.load()
            new SwingGui(controller)
          }


          contents += saveButton
          contents += loadButton
        }


        add(newExitButtons, BorderPanel.Position.Center)
        add(saveLoadButtons, BorderPanel.Position.South)
      }

      val buttomButtons: FlowPanel = new FlowPanel {
        background = java.awt.Color.DARK_GRAY
        val undoButton: Button = new Button("Undo") {
          preferredSize = new Dimension(140, 45)
          background = java.awt.Color.DARK_GRAY.brighter().brighter()
          foreground = java.awt.Color.BLACK
          focusable = false
          selected = false
          border = BorderFactory.createEmptyBorder(10, 10, 10, 10)
          font = new Font("Verdana", 1, 20)

          reactions += {
            case event: ButtonClicked => controller.undo()
          }
        }
        val redoButton: Button = new Button("Redo") {
          preferredSize = new Dimension(140, 45)
          background = java.awt.Color.DARK_GRAY.brighter().brighter()
          foreground = java.awt.Color.BLACK
          focusable = false
          selected = false
          border = BorderFactory.createEmptyBorder(10, 10, 10, 10)
          font = new Font("Verdana", 1, 20)

          reactions += {
            case event: ButtonClicked => controller.redo()
          }
        }
        contents += undoButton
        contents += redoButton
      }

      add(topButtons, BorderPanel.Position.North)
      add(new FreshCardGUI(controller), BorderPanel.Position.Center)
      add(buttomButtons, BorderPanel.Position.South)
    }

    add(playerInfos, BorderPanel.Position.North)
    add(gameControl, BorderPanel.Position.Center)
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

  def gameOver(): Unit = {
    var winner = controller.playfield.players.sortBy(p => p.points > p.points)
    winner = winner.filter(p => p.points >= winner.head.points)
    var winnerList:List[String] = Nil
    for (elem <- winner) {winnerList = elem.name::winnerList}
    winnerList = winnerList.reverse
    val res = Dialog.showConfirmation(contents.head, f"${winnerList.mkString(", ")} won!\nNew Game?", title = "Game Over", optionType = Dialog.Options.YesNo)
    if (res == Dialog.Result.Ok) {
      controller.newGame()
      for {
        x <- cells.indices
        y <- cells.indices
      } {
        cells(x)(y).deafTo(controller)
      }
      playerInfos.deafTo(controller)
      this.deafTo(controller)
      new StartGUI(controller)
      close()
    } else {
      sys.exit(0)
    }
  }

  reactions += {
    case event: GameOver => gameOver()
  }

  contents = new BorderPanel {
    add(banner, BorderPanel.Position.North)
    add(gridPanel, BorderPanel.Position.Center)
    add(controlPanel, BorderPanel.Position.East)
  }
  centerOnScreen()
  visible = true
}
