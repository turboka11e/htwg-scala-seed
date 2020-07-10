package de.htwg.se.Carcassonne.model.fileIOComponent.fileIoJsonImpl

import java.io.{File, PrintWriter}

import de.htwg.se.Carcassonne.model.fileIOComponent.FileIOInterface
import de.htwg.se.Carcassonne.model.gridComponent.{CardInterface, GridInterface}
import de.htwg.se.Carcassonne.model.gridComponent.gridBaseImpl.Grid
import de.htwg.se.Carcassonne.model.playerComponent.Player
import de.htwg.se.Carcassonne.model.playfieldComponent.PlayfieldInterface
import de.htwg.se.Carcassonne.model.playfieldComponent.playfieldBaseImpl.{Playfield, RawCardFactory}
import play.api.libs.json.{JsObject, JsValue, Json, Reads, Writes}

import scala.io.Source

class FileIO extends FileIOInterface {

  override def load: PlayfieldInterface = {

    val source = Source.fromFile("playfield.json")
    val string = source.getLines.mkString
    source.close()
    val json: JsValue = Json.parse(string)

    val size = (json \ "size").as[Int]
    val isOn = (json \ "isOn").as[Int]
    val fresCard = (json \ "freshCard").as[Int]
    val gameState = (json \ "gameState").as[Int]

    implicit val playerReader: Reads[Player] = Json.reads[Player]
    val players: List[Player] = (json \ "players").as[List[Player]]

    var restoredGrid: GridInterface = new Grid(size)

    for (index <- 0 until size * size) {
      val row = (json \\ "row") (index).as[Int]
      val col = (json \\ "col") (index).as[Int]
      val name = (json \ "cards" \\ "name") (index).as[Int]
      val rotation = (json \\ "rotation") (index).as[Int]
      var restoredCard = RawCardFactory("emptyCard").drawCard()
      if (name != -1) {
        restoredCard = RawCardFactory("selectCard", -1, name).drawCard()
      }
      if (((json \ "cards") (index) \ "card" \\ "index").nonEmpty) {
        val i = ((json \ "cards")(index) \ "card" \ "index").as[Int]
        val player = ((json \ "cards")(index) \ "card" \ "player").as[Int]
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

  override def save(playfield: PlayfieldInterface): Unit = {
    val jsonPlayfield = playfieldToJson(playfield)
    val jsonFile = Json.prettyPrint(jsonPlayfield)

    val pw = new PrintWriter(new File("playfield.json"))
    pw.write(jsonFile)
    pw.close()
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
}


