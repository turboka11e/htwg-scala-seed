import org.scalatest.{Matchers, WordSpec}

class GridSpecTest extends WordSpec with Matchers{
  "Carcassonne has a gridbased playingfield. A Grid" when {
    "to be constructed" should {
      "be created with the length of its edges as Size." in {
        val normalGrid = new Grid(1)

      }
    }
  }

}