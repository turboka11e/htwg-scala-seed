package de.htwg.se.Carcassonne.model

import de.htwg.se.Carcassonne.model.gridComponent.{CardInterface, gridBaseImpl}
import de.htwg.se.Carcassonne.model.gridComponent.gridBaseImpl.{Area, Card, Grid, Matrix}
import de.htwg.se.Carcassonne.model.playfieldComponent.playfieldBaseImpl.RawCardFactory
import org.scalatest._

class GridSpec extends WordSpec with Matchers {
  "A Grid is the playingfield of Carcassonne. A Grid" when {
    "to be constructed" should {
      "be created with the length of its edges as size. Practically relevant are size 1, 3, 5, 7, and bigger" in {
        val tinygrid = new Grid(1)
        val smallGrid = new Grid(3)
        val normalGrid = new Grid(5)
        val biggerGrid = new Grid(7)
      }
      "for test purposes only created with a Matrix of Card" in {
        val awkwardGrid = new gridBaseImpl.Grid(4)
        val testGrid = new gridBaseImpl.Grid(4)
      }
    }
    "created properly but empty" should {
      val tinygrid = new Grid(1)
      val smallGrid = new Grid(4)
      val freshCard = RawCardFactory("selectCard", 0).drawCard().finalCard(0, 0)
      val validGrid = smallGrid.place(0, 0, freshCard)
      val matrix = new Matrix[CardInterface](4)

      "give access to its Cells" in {
        tinygrid.card(0, 0) should be(new Card((0, 0)))
        smallGrid.card(0, 0) should be(new Card((0,0)))
        smallGrid.card(0, 1) should be(new Card((0, 1)))
        smallGrid.card(1, 0) should be(new Card((1, 0)))
        smallGrid.card(1, 1) should be(new Card((1, 1)))
      }
      "allow to set individual Cells and remain immutable" in {
        val changedGrid = smallGrid.set(0, 0, Card(List(Area('c', List('n', 'w', 'e', 's'), xy = (0, 0)))))
        changedGrid.card(0, 0) should be(Card(List(Area('c', List('n', 'w', 'e', 's'), xy = (0, 0)))))
        smallGrid.card(0, 0) should be(new Card((0, 0)))
      }
      "place Card in Matrix and update Territories" in {
        smallGrid.place(0, 0, freshCard) should be(validGrid)
      }
//      "add Card to Territories" in {
//        smallGrid.addCardToTerri(0, 0, freshCard, matrix) should be(List(List((2,Area('r',List('w', 'e'),
//          -1,(0,0)))), List((1,Area('g',List('s'),-1,(0,0)))), List((1,Area('c',List('n'),-1,(0,0))))))
//      }
//      "prepare directional tmpTerriList " in {
//        smallGrid.tmpTerriList(0, 0, 'n', freshCard, smallGrid.getTerritories) should be(List(List((0,Area('c',List('n'),-1,(0,0))))))
//      }
//      "return terriotires" in {
//        smallGrid.getTerritories should be(Nil)
//      }
//      "update count of connected Areas of Areas" in {
//        smallGrid.updateTerriEnv(List((0,Area('c',List('n'),-1,(0,0)))), matrix) should be(List((1,Area('c',List('n'),-1,(0,0)))))
//      }
//      "return count of placed Cards" in {
//        smallGrid.getCount should be(0)
//      }
//      "return joinedTerri" in {
//        val tmpTerri:List[List[(Int, Area)]] = List(List((0, Area(0, xy= (1, 1)))))
//          val col_ind = List(Area(0, xy= (1, 1)))
//        smallGrid.insertNeighbourTerrisInJoinedTerri(tmpTerri, col_ind) should be(List((0, Area(0, xy= (1, 1)))))
//      }
//    }
//    "created with Cards placed" in {
//      val card = RawCardFactory("selectCard", 0, 14).drawCard()
//      val filledGrid = new Grid(3).place(1, 1,card.finalCard(1, 1)).place(0, 1, card.finalCard(0, 1))
//      filledGrid.tmpTerriList(1, 1, 'w', card.finalCard(1, 1), filledGrid.getTerritories) should be(
//        List(List((0,Area('c',List('e'),-1,(0,1))), (0,Area('c',List('w'),-1,(1,1)))), List((1,Area('c',List('w'),-1,(0,1)))),
//          List((1,Area('c',List('s'),-1,(0,1)))), List((1,Area('c',List('n'),-1,(0,1)))), List((1,Area('c',List('e'),-1,(1,1)))),
//          List((1,Area('c',List('s'),-1,(1,1)))), List((1,Area('c',List('n'),-1,(1,1))))))
    }
  }
}
