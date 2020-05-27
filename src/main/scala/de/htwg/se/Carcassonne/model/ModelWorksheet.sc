import de.htwg.se.Carcassonne.model.{Area, Player}

val test = Area(value = 'n', corners = List('n'), player = Player("Kalle"))

val territories = List(List(Area))

val area1 = Area()
val area2 = Area()

val area3 = Area()

val cardterritories:List[Area] = List(area1, area2)
