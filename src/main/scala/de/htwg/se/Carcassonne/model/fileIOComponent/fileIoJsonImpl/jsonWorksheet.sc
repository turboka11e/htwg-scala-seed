import java.io.{File, PrintWriter}

import de.htwg.se.Carcassonne.model.gridComponent.gridBaseImpl.Grid
import de.htwg.se.Carcassonne.model.gridComponent.{CardInterface, GridInterface}
import de.htwg.se.Carcassonne.model.playerComponent.Player
import de.htwg.se.Carcassonne.model.playfieldComponent.PlayfieldInterface
import de.htwg.se.Carcassonne.model.playfieldComponent.playfieldBaseImpl.{Playfield, RawCardFactory}
import play.api.libs.json.{JsObject, Json, Reads, Writes}
import play.api.libs.functional.syntax._

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

implicit val cardWrites: Writes[CardInterface] = (card: CardInterface) => {
  var obj = Json.obj(
    "name" -> card.getID._1,
    "rotation" -> card.getID._2)
  val indexArea = card.getAreas.indexWhere(p => p.getPlayer != -1)
  if (indexArea != -1) {
    obj = obj.++(Json.obj(
      "index" -> indexArea,
      "player" -> card.getAreas(indexArea).getPlayer
    ))
  }
  obj
}

implicit val playerWrites: Writes[Player] = (player: Player) => {
  Json.obj(
    "name" -> player.name,
    "points" -> player.points
  )
}

def playfieldToJson(playfield: PlayfieldInterface): JsObject = {
  Json.obj(
    "size" -> playfield.getGrid.getSize,
    "isOn" -> playfield.getIsOn,
    "freshCard" -> playfield.getCurrentFreshCard.getCard.getID._1,
    "gameState" -> playfield.getGameState,
    "players" -> Json.toJson(
      for {
        p <- playfield.getPlayers
      } yield Json.toJson(p)
    ),
    "cards" -> Json.toJson(
      for {
        row <- 0 until playfield.getGrid.getSize
        col <- 0 until playfield.getGrid.getSize
      } yield {
        Json.obj(
          "row" -> row,
          "col" -> col,
          "card" -> Json.toJson(playfield.getGrid.card(row, col))
        )
      }
    )
  )
}

val json = playfieldToJson(playfield)

Json.prettyPrint(json)

(json \\ "index").isDefinedAt(0)
((json \ "cards")(1) \ "card" \\ "index").isEmpty

val index = 0
val row = (json \\ "row")(index).as[Int]
val col = (json \\ "col")(index).as[Int]
val name = (json \ "cards" \\ "name")(index).as[Int]
val rotation = (json \\ "rotation")(index).as[Int]

def load:PlayfieldInterface = {

  val size = (json \ "size").as[Int]
  val isOn = (json \ "isOn").as[Int]
  val fresCard = (json \ "freshCard").as[Int]
  val gameState = (json \ "gameState").as[Int]

  implicit val playerReader: Reads[Player] = Json.reads[Player]
  val players: List[Player] = (json \ "players").as[List[Player]]

  var restoredGrid: GridInterface = new Grid(size)

  for(index <- 0 until size * size){
    val row = (json \\ "row")(index).as[Int]
    val col = (json \\ "col")(index).as[Int]
    val name = (json \ "cards" \\ "name")(index).as[Int]
    val rotation = (json \\ "rotation")(index).as[Int]
    var restoredCard = RawCardFactory("emptyCard").drawCard()
    if(name != -1) {
      restoredCard = RawCardFactory("selectCard", -1, name).drawCard()
    }
    if((json \\ "index").isDefinedAt(index)) {
      val i = (json \\ "index")(index).as[Int]
      val player = (json \\ "player")(index).as[Int]
      restoredCard = RawCardFactory("selectCard", player, name).drawCard()
      restoredCard = restoredCard.setPlayerToArea(i)
    }
    for (_ <- 0 until rotation) {
      restoredCard = restoredCard.rotateRight
    }
    restoredGrid = restoredGrid.set(row, col, restoredCard.finalCard(row, col))
  }
  Playfield(players, isOn, restoredGrid, RawCardFactory("selectCard", isOn, fresCard).drawCard(), gameState)
}

load.equals(playfield)
