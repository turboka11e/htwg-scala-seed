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


  /*************************** SPIELFELD ALS MATRIX ********************************/

  val matrix: Array[Array[Karte]] = Array.ofDim[Karte](x, y)

  val kanten: Array[String] = Array("g", "r", "c")

  def randmatrix(): Unit = {
    for(yy <- 0 until y){
      for(xx <- 0 until x){
          val r: Random.type = Random
          matrix(yy)(xx) = Karte(kanten(r.nextInt(3)), kanten(r.nextInt(3)), kanten(r.nextInt(3)), kanten(r.nextInt(3)))
      }
    }
  }

  def emptymatrix(): Unit = {
    for(yy <- 0 until y){
      for(xx <- 0 until x){
        matrix(xx)(yy) = Karte()
      }
    }
  }

  def insertKarte(x:Int, y:Int):Unit = matrix(x)(y) = Karte("g", "r", "r", "c")

  def matrixfeldgenerator():String = {

    var tmpstring:String = ""

      for(yy <- 0 until y){

        for (xx <- 0 until x) {
          val o = matrix(xx)(yy).o
          tmpstring += s" ┌ $o ┐"
        }
        tmpstring += s"\n"

        for (xx <- 0 until x) {
          val l = matrix(xx)(yy).l
          val r = matrix(xx)(yy).r
          tmpstring += s" $l   $r"
        }
        tmpstring += "\n"

        for (xx <- 0 until x) {
          val u = matrix(xx)(yy).u
          tmpstring += s" └ $u ┘"
        }
        tmpstring += "\n"
      }

    tmpstring
  }

}
