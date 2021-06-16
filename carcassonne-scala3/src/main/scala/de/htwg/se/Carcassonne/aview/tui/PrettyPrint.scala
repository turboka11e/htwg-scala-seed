package de.htwg.se.Carcassonne.aview.tui

import de.htwg.se.Carcassonne.model.gridComponent.GridInterface
import de.htwg.se.Carcassonne.model.playerComponent.Player
import de.htwg.se.Carcassonne.model.playfieldComponent.playfieldBaseImpl.CardManipulator
import de.htwg.se.Carcassonne.controller.GameState
import de.htwg.se.Carcassonne.controller.GameState._

class PrettyPrint(grid: GridInterface, freshCard:CardManipulator, players:List[Player], isOn: Int, success: Boolean) extends PrettyPrintStrategyTemplate {

  private val color = List(Console.BLUE, Console.RED, Console.YELLOW, Console.GREEN)

  var printable: String = g0

  override def printo(gameState: GameState):String =
    gameState match
      case EmptyGame => printable = g0
      case NewGame => printable = g1
      case AddPlayer => printable = g2
      case DrawCard => printable = g3
      case SelectArea => printable = g4
      case PlaceCard => printable = g5
    printable

  def g0:String = "Welcome to Carcassonne\nNeues Spiel mit 'new' starten.\nBitte Feldgröße angeben: "

  def g1:String = "Name eingeben: "

  def g2:String = "Weitere Spieler hinzufügen? [y],[n]\nEingabe: "

  def g3:String = playfieldView + "Karte drehen: Rechts [r], Links [l]\nFertig: [y]\nEingabe: "

  def g4:String = playfieldView + "Männchen auf Gebiet setzen! Blau [0], Rot [1], Gelb [2], Grün [3]\nEingabe: "

  def g5:String = playfieldView + illegalPlace + "Setze Karte in das Spielfeld! [x y]\nEingabe: "

  def playfieldView:String = "\n" + playerLine + nameLine + betweenLine + freshCardPart + restPart

  def playerLine: String =
    var tmpString = ""
    for(i <- players.indices)
      tmpString += color(i) + players(i).toString + Console.RESET + "    "
    tmpString + "\n"

  def illegalPlace:String = if(!success)
      "Keine gültige Platzierung! "
    else
      ""

  def nameLine: String =
    var tmpString = ""
    tmpString = topRow(0) + "\t" + color(isOn) + players(isOn).name + Console.RESET + " ist an der Reihe\n"
    tmpString

  def betweenLine: String = midRow(0) + "\n"

  def freshCardPart: String =
    var tmpString = ""
    tmpString = lowRow(0) + "\tDeine neue Karte:\n" +
      topRow(1) + "\t" + freshCard.card.topString + "\n" +
      midRow(1) + "\t" + freshCard.card.midString + "\n" +
      lowRow(1) + "\t" + freshCard.card.lowString + "\n"
    tmpString

  def restPart: String =
    var tmpString = ""
    for(y <- 2 until grid.getSize)
      tmpString += topRow(y) + "\n" + midRow(y) + "\n" + lowRow(y) + "\n"
    tmpString

  def topRow(y: Int):String =
    var tmpString = ""
    for(x <- 0 until grid.getSize)
      tmpString = tmpString + topSeg(x, y)
    tmpString

  def midRow(y: Int):String =
    var tmpString = ""
    for(x <- 0 until grid.getSize)
      tmpString = tmpString + midSeg(x, y)
    tmpString

  def lowRow(y: Int):String =
    var tmpString = ""
    for(x <- 0 until grid.getSize)
      tmpString = tmpString + lowSeg(x, y)
    tmpString

  def topSeg(x: Int, y: Int):String =
    val o = getColoredCorner(x, y, 'n')
    var ol = "┌"
    var or = "┐"
    if(grid.card(x, y).getCornersLookingFrom('n').contains('w')) ol = o
    if(grid.card(x, y).getCornersLookingFrom('n').contains('e')) or = o
    s" $ol $o $or"

  def midSeg(x: Int, y: Int):String =
    val l = getColoredCorner(x, y, 'w')
    val r = getColoredCorner(x, y, 'e')
    var m = " "
    if(grid.card(x, y).getCornersLookingFrom('e').contains('w')) m = l
    if(grid.card(x, y).getCornersLookingFrom('n').contains('s')) m = getColoredCorner(x, y, 'n')
    s" $l $m $r"

  def lowSeg(x: Int, y: Int):String =
    val u = getColoredCorner(x, y, 's')
    var ul = "└"
    var ur = "┘"
    if(grid.card(x, y).getCornersLookingFrom('s').contains('w')) ul = u
    if(grid.card(x, y).getCornersLookingFrom('s').contains('e')) ur = u
    s" $ul $u $ur"

  def getColoredCorner(x: Int, y: Int, dir:Char):String =

    val playerIndex = grid.card(x, y).getPlayer(dir)
    var tmpString = ""

    if(playerIndex >= 0)
      tmpString = color(playerIndex) + grid.card(x, y).getValue(dir) + Console.RESET
    else
      tmpString = grid.card(x, y).getValue(dir).toString
    tmpString

}
