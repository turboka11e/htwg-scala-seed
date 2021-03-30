package de.htwg.se.Carcassonne.model.fileIOComponent.fileIoXmlImpl

import de.htwg.se.Carcassonne.model.fileIOComponent.FileIOInterface
import de.htwg.se.Carcassonne.model.gridComponent.gridBaseImpl.Grid
import de.htwg.se.Carcassonne.model.playerComponent.Player
import de.htwg.se.Carcassonne.model.playfieldComponent.PlayfieldInterface
import de.htwg.se.Carcassonne.model.playfieldComponent.playfieldBaseImpl.{Playfield, RawCardFactory}

import scala.xml.Elem

class FileIO extends FileIOInterface {

  override def load: PlayfieldInterface = {

    val xml = scala.xml.XML.loadFile("playfield.xml")

    val play = xml \\ "playfield"

    val size = (play \ "@size").text.toInt
    val isOn = (play \ "@isOn").text.toInt
    val freshCard = (play \ "@freshCard").text.toInt
    val gameState = (play \ "@gameState").text.toInt

    var players: List[Player] = Nil
    for (p <- xml \\ "player") {
      val name = (p \ "@name").text
      val points = (p \ "@points").text.toDouble
      players = Player(name, points) :: players
    }
    players = players.reverse

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
        val player = (c \\ "@player").text.toInt
        val index = (c \\ "@index").text.toInt
        restoredCard = RawCardFactory("selectCard", player, name).drawCard()
        restoredCard = restoredCard.setPlayerToArea(index)
      }
      for (_ <- 0 until rotation) {
        restoredCard = restoredCard.rotateRight
      }
      restoredGrid = restoredGrid.set(row, col, restoredCard.finalCard(row, col)).asInstanceOf[Grid]
    }
    Playfield(players, isOn, restoredGrid, RawCardFactory("selectCard", isOn, freshCard).drawCard(), gameState)
  }
  override def save(playfield: PlayfieldInterface): Unit = {

    val xml = <playfield size={playfield.grid.size.toString} isOn={playfield.isOn.toString}
                         freshCard={playfield.freshCard.card.getID._1.toString} gameState={playfield.gameState.toString}>
      <players>
        {for {
        p <- playfield.players
      } yield playerToXml(p.name, p.points)}
      </players>
      <grid>
        {for {
        row <- 0 until playfield.grid.size
        col <- 0 until playfield.grid.size}
        yield cardToXml(row, col, playfield)}
      </grid>
    </playfield>

    scala.xml.XML.save("playfield.xml", xml)
  }

  def playerToXml(name: String, points: Double): Elem = {
    <player name={name} points={points.toString}></player>
  }

  def cardToXml(row:Int, col:Int, playfield: PlayfieldInterface):Elem = {
    val card = playfield.grid.card(row, col)
    val indexArea = card.getAreas.indexWhere(p => p.player != -1)
    <card row={row.toString} col={col.toString} name={card.getID._1.toString} rotation={card.getID._2.toString}>
      {if (indexArea != -1) {
      <selectedArea index={indexArea.toString} player={card.getAreas(indexArea).player.toString}></selectedArea>
    }}
    </card>
  }

}
