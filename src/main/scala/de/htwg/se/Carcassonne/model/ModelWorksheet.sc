import de.htwg.se.Carcassonne.model.{Area, Card}

val neu = Card(List(Area('c', List('n', 's', 'w')))).areas

val areasrotated = neu.map{ x => x.rotateRight()}