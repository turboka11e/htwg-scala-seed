package de.htwg.se.Carcassonne

import de.htwg.se.Carcassonne.aview.tui.TUI
import de.htwg.se.Carcassonne.controller.controllerComponent.{Controller, NewGame}
import de.htwg.se.Carcassonne.model.playfieldComponent.playfieldBaseImpl.Playfield

import scala.io.StdIn._

@main def Carcassonne =

  val controller = new Controller(new Playfield)
  val tui = new TUI(controller)

  var input: String = ""
 
  while (input != "q") do 
    input = readLine()
    tui.processInputLine(input)
    
  

  

