package de.htwg.se.Carcassonne.model

case class GridCreator(private val grid: Grid, private val allPlayers:List[Player]){

  def this(size:Int) = this(new Grid(size), allPlayers = Nil)

  def addPlayer(player:String):GridCreator = copy(grid.copy(playerlist = Player(player)::allPlayers),
    allPlayers =  Player(player)::allPlayers)

  def getPlayerReadyGrid:Grid = grid
  
}
