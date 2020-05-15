case class Card(nord:Char = ' ', west:Char = ' ', east:Char = ' ', south:Char = ' ')

val cell1 = Card('g', 'c', 'c', 'r')
val cell2 = Card()

case class Matrix[T] (rows:Vector[Vector[T]]) {
  def this(size:Int, filling:T) = this(Vector.tabulate(size, size){(row, col) => filling})
  val size:Int = rows.size
  def card(row:Int, col:Int):T = rows (row)(col)
  def fill (filling:T):Matrix[T]= this.copy( Vector.tabulate(size, size){(row, col) => filling})
  def replaceCell(row:Int, col:Int, cell:T):Matrix[T] = copy(rows.updated(row, rows(row).updated(col, cell)))
}

var karten = new Matrix(2, cell1)
karten.size
karten.card(1, 1)
karten = karten.replaceCell(1, 1, cell2)
karten.card(1, 1)

case class Grid(private val cells:Matrix[Card]) {
  def this(size: Int) = this(new Matrix[Card](size, Card()))

  val size: Int = cells.size

  def cell(row: Int, col: Int): Card = cells.card(row, col)

  def set(row: Int, col: Int, nord:Char, west:Char, east:Char, south:Char): Grid = copy(cells.replaceCell(row, col, Card(nord, west, east, south)))
}

var gamefield = new Grid(3)
var g2 = gamefield.set(1, 1, 'c', 'c', 'c', 'c')

g2.cell(1, 1)
g2.size