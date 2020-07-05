import de.htwg.se.Carcassonne.model.gridComponent.CardInterface
import de.htwg.se.Carcassonne.model.gridComponent.gridBaseImpl.Card
import de.htwg.se.Carcassonne.model.playfieldComponent.PlayfieldInterface
import de.htwg.se.Carcassonne.model.playfieldComponent.playfieldBaseImpl.RawCardFactory
import play.api.libs.json.{Json, Writes}

implicit val cardWrites = new Writes[CardInterface] {
  override def writes(card: CardInterface) = {
    var obj = Json.obj(
        "name" -> card.getID._1,
        "rotation" -> card.getID._2)
    val indexArea = card.getAreas.indexWhere(p => p.getPlayer != -1)
    if(indexArea != -1) {
      obj = obj.++(Json.obj(
        "index" -> indexArea,
        "player" -> card.getAreas(indexArea).getPlayer
      ))
    }
    obj
  }
}

val testCard = RawCardFactory("selectCard", 1, 14).drawCard().rotateRight.setPlayerToArea(1).finalCard(0, 0)
val emptyCard = RawCardFactory("emptyCard").drawCard().finalCard(0, 0)
val test1 = Json.toJson(testCard)
val test2 = Json.toJson(emptyCard)

(test1 \ "player").toOption.getOrElse(None)
(test2 \ "player").toOption.getOrElse(None)