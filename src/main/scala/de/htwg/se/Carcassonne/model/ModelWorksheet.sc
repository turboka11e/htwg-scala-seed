import de.htwg.se.Carcassonne.model.gridComponent.gridBaseImpl.Grid
import de.htwg.se.Carcassonne.model.playfieldComponent.playfieldBaseImpl.RawCardFactory

var testGrid = new Grid(3)

val card = RawCardFactory("selectCard", -1, 14).drawCard().finalCard(0, 0)

val g1 = testGrid.place(0, 0, card)

g1.getTerritories