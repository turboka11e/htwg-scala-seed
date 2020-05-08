package de.htwg.se.Carcassonne.model

import scala.util.Random

case class Spielfeld(x: Int, y: Int) {

  /* Aufbau einzelner Block */
  def block():String = s" ┌ g ┐\n g   c \n └ r ┘\n"

  def obereZeile(oben:String):String = s" ┌ $oben ┐" * x

  def mittlZeile(links:String, rechts:String):String = s" $links   $rechts" * x

  def unterZeile(unten:String):String = s" └ $unten ┘" * x

  def reihengenerator(oben:String, links:String, rechts:String, unten:String):String = {
    obereZeile(oben) + "\n" + mittlZeile(links, rechts) + "\n" + unterZeile(unten)
  }

  def feldgenerator(oben:String, links:String, rechts:String, unten:String):String = {
    (reihengenerator(oben, links, rechts, unten) + "\n") * y
  }


  /*************************** WORK IN PROGRESS ********************************/

  val matrix: Array[Array[String]] = Array.ofDim[String](x, y)

  val kanten: Array[String] = Array("g", "c", "r")

  for(yy <- 0 until y){
    for(xx <- 0 until x){
      val r: Random.type = Random
      matrix(yy)(xx) = kanten(r.nextInt(3))
    }
  }

  def matrixfeldgenerator():String = {

    var tmpstring:String = ""

      for(yy <- 0 until y * 3 by 3){

        for (xx <- 0 until x) {
          val o = matrix(yy / 3)(xx)
          tmpstring += s" ┌ $o ┐"
        }
        tmpstring += s" yy: $yy\n"

        for (xx <- 0 until x) {
          val l = matrix(yy / 3)(xx)
          val r = matrix(yy / 3)(xx)
          tmpstring += s" $l   $r"
        }
        tmpstring += "\n"

        for (xx <- 0 until x) {
          val u = matrix(yy / 3)(xx)
          tmpstring += s" └ $u ┘"
        }
        tmpstring += "\n"
      }

    tmpstring
  }

}
