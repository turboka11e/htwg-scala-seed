package de.htwg.se.Carcassonne

import de.htwg.se.Carcassonne.aview.TUI
import de.htwg.se.Carcassonne.model.{Area, Card, CardCreator, Grid, GridCreator, Player}
import scala.io.StdIn._

@main def Carcassonne = 
  var grid: Grid = new Grid(1)
  val tui = new TUI

  var playerList: List[Player] = Nil
  var playerIsOn: Int = 0

  var input: String = ""
  var preGrid = new GridCreator(3)
  var gameMode = 0
  var freshCard = CardCreator(Player("Empty"))

  while
    input != "q"
  do 
    gameMode match
      case 0 =>
        print(tui.welcomeMessage)
        print("\nFeldgröße auswählen: ")
        input = readLine()
        if (input forall Character.isDigit)
          preGrid = tui.processGridSize(input.toInt)
          gameMode += 1
        
      case 1 =>
        print("\nSpieler hinzufügen!\n")

        while 
          (input == "Y" || input == "y")
            grid = preGrid.getPlayerReadyGrid
            playerList = grid.getPlayerlist
            print(grid.toString)
            gameMode += 1
        do
          print("Name eingeben: ")
          input = readLine()
          preGrid = tui.processAddingPlayer(input, preGrid)
          print("Weitere Spieler hinzufügen?\n('Y', 'N') Eingabe: ")
          input = readLine()

      case 2 =>
        val currentplayer = playerList(playerIsOn)
        print(s"\nSpieler $currentplayer ist dran. Hier ist deine Karte: \n")
        freshCard = CardCreator(playerList(playerIsOn)).randCard
        print(freshCard.showCard)
        gameMode = 3

      case 3 =>
        print("\nKarte rotieren?\nRechts: 'r' Links:'l' Nein: 'n'\nEingabe: ")
        input = readLine()
        freshCard = tui.processRotateCard(input, freshCard)
        if (input == "r" || input == "l") 
          gameMode = 3
        else 
          gameMode = 4
        

      case 4 =>
        print("\nMännchen auf Gebiet in der Karte setzen?\n")
        print(freshCard.showCard)
        print(freshCard.showTerri)
        print("\nGebiet über Zahl auswählen: ")
        input = readLine()
        freshCard = tui.processSelectArea(input, freshCard)
        print(freshCard.showTerri)
        gameMode = 5

      case 5 =>
        print("\nKarte in Spielfeld einsetzen: \n")
        print(grid.toString)
        freshCard.showCard
        print("\nX und Y Koordinate ('x y') eingeben: ")
        input = readLine()
        val xy = input.split(" ")
        val addedCheck = grid.getCount
        grid = tui.processPlacingCard(xy, grid, freshCard)
        if (addedCheck == grid.getCount) 
          print("Kein gültiger Zug. Nochmal versuchen!\n")
        else 
          print(grid.toString)
          if (playerIsOn == playerList.size - 1) 
            playerIsOn = 0
          else 
            playerIsOn += 1
          
          gameMode = 2
          print("\n" + grid.summonTerritories.toString)
        
       
  
