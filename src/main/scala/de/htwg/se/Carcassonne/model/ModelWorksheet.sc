import de.htwg.se.Carcassonne.model.{Area, Card, Player}

val test = Area(value = 'n', corners = List('n'), player = Player("Kalle"))

val territories = List(List(Area))

val area1 = Area()
val area2 = Area()

val area3 = Area()

val cardterritories:List[Area] = List(area1, area2)

val oneCard = Card(List(Area('r', List('w', 'e')), Area('c', List('n')), Area('g', List('s'))))
val twoCard = Card(List(Area('r', List('w', 'e')), Area('c', List('n')), Area('g', List('s'))))
val threeCard = Card(List(Area('r', List('w', 'e')), Area('c', List('n')), Area('g', List('s'))))

oneCard.areas.ne(twoCard.areas)
val input = "3"
val xy = input.split(" ")
val x = xy.apply(0).toInt

Console.out.println( "Test " + Console.RED + " RED " + Console.RESET )