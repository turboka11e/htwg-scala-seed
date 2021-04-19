name          := "TUI"
organization  := "de.htwg.se"
version       := "0.7.0"
scalaVersion  := "2.12.2"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.5"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"

//*******************************************************************************//
//Libraries that we will use in later lectures compatible with this scala version
// uncomment to use!!

libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "2.1.1"

libraryDependencies += "com.google.inject" % "guice" % "4.1.0"

libraryDependencies += "net.codingwell" %% "scala-guice" % "4.1.0"

libraryDependencies += "org.scala-lang.modules" % "scala-xml_2.12" % "1.0.6"

libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.6"

libraryDependencies += "com.typesafe.akka" %% "akka-http" % "10.2.4"

libraryDependencies += "com.typesafe.akka" %% "akka-actor-typed" % "2.6.8"

libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.6.8"


