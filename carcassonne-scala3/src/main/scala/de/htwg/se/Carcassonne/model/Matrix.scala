package de.htwg.se.Carcassonne.model

case class Matrix[T] (rows:Vector[Vector[T]], private val count:Int = 0) {
  def this(size:Int, filling:T) = this(Vector.tabulate(size, size){(row, col) => filling})

  val size:Int = rows.size

  def getCount:Int = count

  def card(row:Int, col:Int):T = rows (row)(col)

  //def fill (filling:T):Matrix[T]= this.copy( Vector.tabulate(size, size){(row, col) => filling})

  def replaceCell(row:Int, col:Int, cell:T):Matrix[T] = {
    copy(rows.updated(row, rows(row).updated(col, cell)), count + 1)
  }
}