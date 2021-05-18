import scala.util.Random
//import de.htwg.se.Carcassonne.model.playerComponent.Player
//
//val pList = List(Player("Mark", 2), Player("Nail", 2), Player("Kalle", 1))
//
//var winner = pList.sortBy(p => p.points > p.points)
//winner = winner.filter(p => p.points >= winner.head.points)
//var winnerList:List[String] = Nil
//for (elem <- winner) {winnerList = elem.name::winnerList}
//winnerList.mkString(", ")

object OneTimeName {
  def apply(): String = {
    Random.alphanumeric.take(10).mkString
  }
}

println(s"${OneTimeName.apply()}")
println(s"${OneTimeName.apply()}")