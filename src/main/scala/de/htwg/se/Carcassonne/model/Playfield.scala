package de.htwg.se.Carcassonne.model

case class Playfield(players:List[Player] = Nil, isOn: Int = 0, grid: Grid = new Grid(1),
                     freshCard:CardCreator = new CardCreator(), gameState:Int =  0, success:Boolean = true) {

  def changeGameState(gs:Int):Playfield = copy(gameState = gs)

  def getGameState:Int = gameState

  def getSuccess:Boolean = success

  def fieldSize(size:Int):Playfield = copy(grid = new Grid(size), gameState = 1)                    // GameState 0

  def addPlayer(name:String):Playfield = copy(players = players ::: List(Player(name)), gameState = 2)       // GameState 1

  def getFreshCard:Playfield = copy(freshCard = CardCreator(isOn).randCard)                // GameState 2

  def rotateR:Playfield = copy(freshCard = freshCard.rotateRight)                                   // Gamestate 3

  def rotateL:Playfield = copy(freshCard = freshCard.rotateLeft)                                    // Gamestate 3

  def selectArea(nr:Int):Playfield = copy(freshCard = freshCard.setPlayerToArea(nr), gameState = 5)                // Gamestate 4

  def placeCard(x: Int, y: Int): Playfield = { // Gamestate 5
    copy(success = true)
    val check = grid.getCount
    val CardAdded = grid.place(x, y, freshCard.finalCard(x, y))
    if (check == CardAdded.getCount){
      copy(success = false)
    }else{
      copy(grid = CardAdded, gameState = 3, players = Points().updatePoints(CardAdded.getTerritories, players))
    }
  }

  def nextPlayer: Playfield = {
    if(isOn == players.size - 1){
      copy(isOn = 0)
    } else {
      copy(isOn = isOn + 1)
    }
  }

  def playFieldToString:String = PrettyPrint(gameState, grid, freshCard, players, isOn, success).toString




}
