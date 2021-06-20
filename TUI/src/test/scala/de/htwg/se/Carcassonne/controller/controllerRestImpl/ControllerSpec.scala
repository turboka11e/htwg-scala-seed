package de.htwg.se.Carcassonne.controller.controllerRestImpl
import org.scalatest._

case class ControllerSpec() extends WordSpec with Matchers {

  "+++ Blueprint +++" should {
    val test = 1
    "+++ Blueprint +++" in {
      test should be(2)
    }
  }
}