package de.htwg.se.Carcassonne.model.gridComponent.gridBaseImpl

import de.htwg.se.Carcassonne.model.gridComponent.{AreaInterface, CardInterface}

trait CardFactory {
  def drawCard(): CardInterface

  val cards = List(

    (List(("r", List("w", "e")), ("c", List("n")), ("g", List("s"))), "D"), // D

    (List(("c", List("n")), ("g", List("s", "w", "e"))), "E"), // E

    (List(("c", List("w", "e")), ("g", List("s")), ("g", List("n"))), "G"), // G

    (List(("c", List("w")), ("c", List("e")), ("g", List("n", "s"))), "H"), // H

    (List(("c", List("n")), ("c", List("e")), ("g", List("s", "w"))), "I"), // I

    (List(("c", List("n")), ("g", List("w")), ("r", List("s", "e"))), "J"), // J

    (List(("c", List("n")), ("g", List("e")), ("r", List("s", "w"))), "K"), // K

    (List(("c", List("n")), ("r", List("s")), ("r", List("w")), ("r", List("e"))), "L"), // L

    (List(("c", List("n", "w")), ("g", List("s", "e"))), "N"), // N

    (List(("c", List("n", "w")), ("r", List("s", "e"))), "O"), // O

    (List(("c", List("n", "w", "e")), ("g", List("s"))), "R"), // R

    (List(("c", List("n", "w", "e")), ("r", List("s"))), "T"), // T

    (List(("r", List("n", "s")), ("g", List("e")), ("g", List("w"))), "U"), // U

    (List(("r", List("w", "s")), ("g", List("n", "e"))), "V"), // V

    (List(("c", List("n")), ("c", List("w")), ("c", List("e")), ("c", List("s"))), "C") // all c

  )
}

private class EmptyCard() extends CardFactory {
  override def drawCard(): CardInterface = Card(None, None)
}

private class RandomCard() extends CardFactory {
  override def drawCard():
}

private class NonEmptyCard(k: String, rotation: Int, areaID: (Int, Int), playerSelect: Option[(Int, Int)]) extends CardFactory {

  val template: (List[(String, List[String])], String) = cards.find(p => p._2 == k).get
  var areas: List[AreaInterface] = Nil
  for {
    (area, i) <- template._1.zipWithIndex
  } {
    if (playerSelect.isDefined) {
      if (i == playerSelect.get._2) {
        Area(area._1, area._2, Option(playerSelect.get._1), areaID)::areas
      }
    }
    Area(area._1, area._2, None, areaID)::areas
  }
  override def drawCard(): CardInterface = Card(Option(areas), Option(k, rotation))
}

object CardFactory {
  def apply(kind: String, rotation: Int, areaID: (Int, Int), playerSelect: Option[(Int, Int)]): CardFactory = kind match {
    case k => new NonEmptyCard(k, rotation, areaID, playerSelect)
  }
  def apply(kind:String):CardFactory = kind match {
    case "emptyCard" => new EmptyCard()
    case "randomCard" => new RandomCard()
  }
}



