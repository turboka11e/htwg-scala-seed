import de.htwg.se.Carcassonne.model.gridComponent.GridInterface
import de.htwg.se.Carcassonne.model.gridComponent.gridBaseImpl.Grid
import de.htwg.se.Carcassonne.model.playerComponent.Player
import de.htwg.se.Carcassonne.model.playfieldComponent.playfieldBaseImpl.{Playfield, RawCardFactory}

import scala.xml.Elem

var playfield = new Playfield()

playfield = playfield.fieldSize(2)

playfield = playfield.addPlayer("Lukas")
playfield = playfield.addPlayer("Basti")
playfield = playfield.getFreshCard(14)
playfield = playfield.rotateR
playfield = playfield.selectArea(1)
playfield = playfield.placeCard(0, 0)
playfield = playfield.getFreshCard(14)
playfield = playfield.placeCard(0, 1)

def playerToXml(name: String, points: Double): Elem = {
  <player name={name}>
    {points.toString}
  </player>
}

def cardToXml(row:Int, col:Int):Elem = {
  val card = playfield.grid.card(row, col)
  val indexArea = card.getAreas.indexWhere(p => p.getPlayer != -1)
  <card row={row.toString} col={col.toString} name={card.getID._1.toString} rotation={card.getID._2.toString}>
    {if (indexArea != -1) {
      <selectedArea index={indexArea.toString} player={card.getAreas(indexArea).getPlayer.toString}></selectedArea>
  }}
  </card>
}

val xml = <playfield size={playfield.grid.size.toString} isOn={playfield.isOn.toString} freshCard={playfield.freshCard.getCard.getID._1.toString} gameState={playfield.getGameState.toString}>
  <players>
    {for {
    p <- playfield.getPlayers
  } yield playerToXml(p.name, p.points)}
  </players>
  <grid>
    {for {
    row <- 0 until playfield.grid.size
    col <- 0 until playfield.grid.size}
    yield cardToXml(row, col)}
  </grid>
</playfield>



def load(xml: Elem): Playfield = {

  val play = xml \\ "playfield"

  val size = (play \ "@size").text.toInt
  val isOn = (play \ "@isOn").text.toInt
  val freshCard = (play \ "@freshCard").text.toInt
  val gameState = (play \ "@gameState").text.toInt

  var players: List[Player] = Nil
  for (p <- xml \\ "player") {
    val name = (p \ "@name").text
    val points = p.child(1).text.toDouble
    players = Player(name, points) :: players
  }

  var restoredGrid: Grid = new Grid(size)

  for (c <- xml \\ "card") {
    val row = (c \ "@row").text.toInt
    val col = (c \ "@col").text.toInt
    val name = (c \ "@name").text.toInt
    val rotation = (c \ "@rotation").text.toInt
    var restoredCard = RawCardFactory("emptyCard").drawCard()
    if (name != -1) {
      restoredCard = RawCardFactory("selectCard", -1, name).drawCard()
    }
    if (c.child.isDefinedAt(2)) {
      val player = (c \\ "@index").text.toInt
      val select = (c \\ "@player").text.toInt
      restoredCard = RawCardFactory("selectCard", player, name).drawCard()
      restoredCard = restoredCard.setPlayerToArea(select)
    }
    for (_ <- 0 until rotation) {
      restoredCard = restoredCard.rotateRight
    }
    restoredGrid = restoredGrid.set(row, col, restoredCard.finalCard(row, col)).asInstanceOf[Grid]
  }
  Playfield(players, isOn, restoredGrid, RawCardFactory("selectCard", isOn, freshCard).drawCard(), gameState)
}

load(xml)