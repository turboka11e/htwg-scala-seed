package de.htwg.se.Carcassonne.model

case class Card(areas:List[Area] = List(Area(corners = List('n')), Area(corners = List('w')), Area(corners = List('e')), Area(corners = List('s')))){

  def isEmpty: Boolean = areas.head.getValue == ' ' && areas.size == 4

  def getValue(dir:Char): Char = areas.find(_.getCorners.contains(dir)).get.getValue

  def getLinksLookingFrom(dir:Char): List[Char] = areas.find(_.getCorners.contains(dir)).get.getCorners

  def getAreaLookingFrom(dir:Char): Area = areas.find(_.getCorners.contains(dir)).get

  def isValid: Boolean = {
    var check = true
    for(x <- areas)
      for(y <- areas)
        for(c <- x.getCorners){
          if(y.getCorners.contains(c)  && y != x){
            check = false
          }
        }
    check
  }

}

