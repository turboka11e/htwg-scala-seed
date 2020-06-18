package de.htwg.se.Carcassonne.model

import scala.util.Random

trait RawCardFactory {
  def drawCard(): CardCreator
  val randomCards: List[Card] = List(

    Card(List(Area('r', List('w', 'e')), Area('c', List('n')), Area('g', List('s')))),       // D

    Card(List(Area('c', List('n')), Area('g', List('s', 'w', 'e')))),                        // E

    Card(List(Area('c', List('w', 'e')), Area('g', List('s')), Area('g', List('n')))),       // F

    Card(List(Area('c', List('w')), Area('c', List('e')), Area('g', List('n', 's')))),       // H

    Card(List(Area('c', List('n', 'e')), Area('g', List('s', 'w')))),                        // I

    Card(List(Area('c', List('n')), Area('g', List('w')), Area('r', List('s', 'e')))),       // J

    Card(List(Area('c', List('n')), Area('g', List('e')), Area('r', List('s', 'w')))),       // K

    Card(List(Area('c', List('n')), Area('r', List('s', 'w', 'e')))),                        // L

    Card(List(Area('c', List('n', 'w')), Area('g', List('s', 'e')))),                        // M

    Card(List(Area('c', List('n', 'w')), Area('r', List('s', 'e')))),                        // O

    Card(List(Area('c', List('n', 'w', 'e')), Area('g', List('s')))),                        // R

    Card(List(Area('c', List('n', 'w', 'e')), Area('r', List('s')))),                        // T

    Card(List(Area('r', List('n', 's')), Area('g', List('e')), Area('g', List('w')))),      // U

    Card(List(Area('r', List('w', 's')), Area('g', List('n', 'e')))),                        // V

    Card(List(Area('c', List('n')), Area('c', List('w')), Area('c', List('e')), Area('c', List('s'))))  // all c

  )
}

private class EmptyCard() extends RawCardFactory {
  override def drawCard():CardCreator = new CardCreator()
}

private class RandomCard(isOn: Int) extends RawCardFactory {
  override def drawCard(): CardCreator = CardCreator(isOn, randomCards(Random.nextInt(randomCards.size)))
}

private class SelectCard(isOn: Int, select:Int) extends RawCardFactory {
  override def drawCard(): CardCreator = CardCreator(isOn, randomCards(select))
}

object RawCardFactory {
  def apply(kind: String, isOn:Int = -1, select:Int = 0): RawCardFactory = kind match {
    case "emptyCard" => new EmptyCard()
    case "randomCard" => new RandomCard(isOn)
    case "selectCard" => new SelectCard(isOn, select)
  }
}



