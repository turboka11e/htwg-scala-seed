package de.htwg.se.Carcassonne.aview.tui

class TUI()  {



//  listenTo(controller)
//
//  def processInputLine(input: String): Unit = {
//    input match {
//      case "q" =>
//      case "new" => controller.newGame()
//      case "y" => decide(true)
//      case "n" => decide(false)
//      case "r" => controller.rotateRight()
//      case "l" => controller.rotateLeft()
//      case "redo" => controller.redo()
//      case "undo" => controller.undo()
//      case _ => validateLongString(input)
//    }
//  }
//
//  def decide(dc: Boolean): Unit = {
//    controller.getGameState match {
//      case 2 =>
//        if (!dc) {
//          controller.firstCard()
//        } else {
//          controller.changeGameState(1)
//          controller.publish(new PlayfieldChanged)
//        }
//      case 3 => if (dc) controller.changeGameState(4)
//        controller.publish(new PlayfieldChanged)
//
//      case 4 => if (!dc) controller.changeGameState(5)
//        controller.publish(new PlayfieldChanged)
//      case _ =>
//    }
//  }

//
//  def validateLongString(input: String): Unit = {
//    if (input.forall(p => p.isDigit || p == ' ') && input.nonEmpty) {
//      val extract = input.split(" ")
//      extract.length match {
//        case 1 => Try(Integer.parseInt(extract.head)) match {
//          case Success(digit) => forkDigit(digit)
//          case Failure(exception) => println("Could not parse integer! {}", exception);
//        }
//        case 2 => Try((extract(0).toInt, extract(1).toInt)) match {
//          case Success((x, y)) =>
//            controller.placeCard(x, y)
//            controller.placeAble()
//          case Failure(exception) => println("Could not parse integer! Error: {}", exception);
//        }
//        case _ =>
//      }
//    } else if (input.nonEmpty) {
//      if (controller.getGameState == 1) {
//        controller.addPlayer(input)
//      } else {
//        if (controller.getGameState > 1) controller.publish(new InvalidInputString)
//      }
//    }
//  }
//
//  def forkDigit(input: Int): Unit = {
//    controller.getGameState match {
//      case 0 => controller.createGrid(input)
//      case 4 => controller.selectArea(input)
//      case _ =>
//    }
//  }
//
//  reactions += {
//    case event: SetGrid => printTui()
//    case event: AddPlayers => printTui()
//    case event: FirstCard => printTui()
//    case event: PlayfieldChanged => printTui()
//    case event: GameOver => println("GAME OVER")
//    case event: InvalidInputString => printErrorTUI()
//  }
//
//  def printTui(): Unit = {
//    println(Console.RED + "*** " + controller.statusText + " ***" + Console.RESET)
//    //    print(new PrettyPrint(controller.getPlayfield).printo())    // comment out, if only statusText is desired
//  }
//
//  def printErrorTUI(): Unit = {
//    println(Console.RED + "*** " + "Ungültige Eingabe! Den Anweisungen folgen!" + " ***" + Console.RESET)
//    //    print(new PrettyPrint(controller.getPlayfield).printo())    // comment out, if only statusText is desired
//  }

}
