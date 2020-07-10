package de.htwg.se.Carcassonne

import com.google.inject.{Guice, Injector}
import de.htwg.se.Carcassonne.aview.gui.StartGUI
import de.htwg.se.Carcassonne.aview.tui.TUI
import de.htwg.se.Carcassonne.controller.controllerComponent.ControllerInterface

import scala.io.StdIn._

object Carcassonne {

  val injector: Injector = Guice.createInjector(new CarcassonneModule)
  val controller: ControllerInterface = injector.getInstance(classOf[ControllerInterface])
  val tui = new TUI(controller)
//  val gui = new StartGUI(controller)

  def main(args: Array[String]): Unit = {
    var input: String = ""
    if(args.length > 0) input = args(0)

    if (!input.isEmpty){
      tui.processInputLine(input)
    }
    else {
      do {
        input = readLine()
        tui.processInputLine(input)
      } while (input != "q")
    }

  }
}
