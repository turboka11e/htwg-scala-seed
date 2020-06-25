package de.htwg.se.Carcassonne.aview.gui

import java.awt.image.BufferedImage
import java.io.File

import de.htwg.se.Carcassonne.controller.Controller
import de.htwg.se.Carcassonne.model.{Area, Card}
import javax.imageio.ImageIO
import javax.swing.ImageIcon

import scala.swing.{GridPanel, Label}

class GuiCard(controller: Controller, line:Int, row:Int) extends Label{

  var img:BufferedImage = findImage()

  icon = new ImageIcon(findImage())

  def setCard(): Unit = {
    icon = new ImageIcon(img)
  }

  def findImage():BufferedImage = {

    val numToCharImage = List("D", "E", "G", "H", "I", "J", "K", "L", "L", "N", "O", "R", "T", "U", "V", "W")

    val randomCards: List[Card] = List(

      Card(List(Area('r', List('w', 'e')), Area('c', List('n')), Area('g', List('s')))),       // D

      Card(List(Area('c', List('n')), Area('g', List('s', 'w', 'e')))),                        // E

      Card(List(Area('c', List('w', 'e')), Area('g', List('s')), Area('g', List('n')))),       // G

      Card(List(Area('c', List('w')), Area('c', List('e')), Area('g', List('n', 's')))),       // H

      Card(List(Area('c', List('n', 'e')), Area('g', List('s', 'w')))),                        // I

      Card(List(Area('c', List('n')), Area('g', List('w')), Area('r', List('s', 'e')))),       // J

      Card(List(Area('c', List('n')), Area('g', List('e')), Area('r', List('s', 'w')))),       // K

      Card(List(Area('c', List('n')), Area('r', List('s', 'w', 'e')))),                        // L

      Card(List(Area('c', List('n', 'w')), Area('g', List('s', 'e')))),                        // N

      Card(List(Area('c', List('n', 'w')), Area('r', List('s', 'e')))),                        // O

      Card(List(Area('c', List('n', 'w', 'e')), Area('g', List('s')))),                        // R

      Card(List(Area('c', List('n', 'w', 'e')), Area('r', List('s')))),                        // T

      Card(List(Area('r', List('n', 's')), Area('g', List('e')), Area('g', List('w')))),      // U

      Card(List(Area('r', List('w', 's')), Area('g', List('n', 'e')))),                        // V

      Card(List(Area('c', List('n')), Area('c', List('w')), Area('c', List('e')), Area('c', List('s'))))  // all c

    )



    controller.playfield.grid.card(line, row).areas






    ImageIO.read(new File("./src/main/scala/de/htwg/se/Carcassonne/aview/media/Empty.png"))
  }
}
