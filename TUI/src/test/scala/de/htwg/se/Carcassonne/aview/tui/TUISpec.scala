package de.htwg.se.Carcassonne.aview.tui

import de.htwg.se.Carcassonne.controller.controllerRestImpl.Controller
import org.mockito.Mockito.{times, verify, when}
import org.scalatest._
import org.scalatest.mockito.MockitoSugar

class TUISpec extends WordSpec with Matchers with MockitoSugar {

  "A tui" when {
    val mockedController = mock[Controller]
    val tui = new TUI(mockedController)

    "initialized" should {
      tui.processInputLine("new")
      "start a new Game" in {
        verify(mockedController, times(1)).newGame()
      }
    }
  }

}
