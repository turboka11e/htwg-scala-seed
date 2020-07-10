package de.htwg.se.Carcassonne.model.playfieldComponent.playfieldBaseImpl

import de.htwg.se.Carcassonne.model.gridComponent.gridBaseImpl.{Area, Card}
import de.htwg.se.Carcassonne.model.playfieldComponent.playfieldBaseImpl

import scala.util.Random

trait RawCardFactory {
  def drawCard(): CardManipulator
  val randomCards: List[Card] = List(

    Card(List(Area('r', List('w', 'e')), Area('c', List('n')), Area('g', List('s'))), (0, 0)),       // D

    Card(List(Area('c', List('n')), Area('g', List('s', 'w', 'e'))), (1, 0)),                        // E

    Card(List(Area('c', List('w', 'e')), Area('g', List('s')), Area('g', List('n'))), (2, 0)),       // G

    Card(List(Area('c', List('w')), Area('c', List('e')), Area('g', List('n', 's'))), (3, 0)),       // H

    Card(List(Area('c', List('n')), Area('c', List('e')), Area('g', List('s', 'w'))), (4, 0)),       // I

    Card(List(Area('c', List('n')), Area('g', List('w')), Area('r', List('s', 'e'))), (5, 0)),       // J

    Card(List(Area('c', List('n')), Area('g', List('e')), Area('r', List('s', 'w'))), (6, 0)),       // K

    Card(List(Area('c', List('n')), Area('r', List('s')), Area('r', List('w')), Area('r', List('e'))), (7, 0)),   // L

    Card(List(Area('c', List('n', 'w')), Area('g', List('s', 'e'))), (8, 0)),                        // N

    Card(List(Area('c', List('n', 'w')), Area('r', List('s', 'e'))), (9, 0)),                        // O

    Card(List(Area('c', List('n', 'w', 'e')), Area('g', List('s'))), (10, 0)),                        // R

    Card(List(Area('c', List('n', 'w', 'e')), Area('r', List('s'))), (11, 0)),                        // T

    Card(List(Area('r', List('n', 's')), Area('g', List('e')), Area('g', List('w'))), (12, 0)),      // U

    Card(List(Area('r', List('w', 's')), Area('g', List('n', 'e'))), (13, 0)),                        // V

    Card(List(Area('c', List('n')), Area('c', List('w')), Area('c', List('e')), Area('c', List('s'))), (14, 0))  // all c

  )
}

private class EmptyCard() extends RawCardFactory {
  override def drawCard():CardManipulator = new CardManipulator()
}

private class RandomCard(isOn: Int) extends RawCardFactory {
  override def drawCard(): CardManipulator = CardManipulator(isOn, randomCards(Random.nextInt(randomCards.size)))
}

private class SelectCard(isOn: Int, select:Int) extends RawCardFactory {
  override def drawCard(): CardManipulator = playfieldBaseImpl.CardManipulator(isOn, randomCards(select))
}

object RawCardFactory {
  def apply(kind: String, isOn:Int = -1, selectedCard:Int = 0): RawCardFactory = kind match {
    case "emptyCard" => new EmptyCard()
    case "randomCard" => new RandomCard(isOn)
    case "selectCard" => new SelectCard(isOn, selectedCard)
  }
}



