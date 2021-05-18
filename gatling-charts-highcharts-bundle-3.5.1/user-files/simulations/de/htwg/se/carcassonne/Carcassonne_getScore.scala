package de.htwg.se.carcassonne

import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class CarcassonneGetScore extends Simulation {

	val httpProtocol = http
		.baseUrl("http://localhost:8080")
		.inferHtmlResources()
		.acceptHeader("*/*")
		.acceptEncodingHeader("gzip, deflate")
		.userAgentHeader("PostmanRuntime/7.28.0")

	val scn = scenario("Carcassonne getScore")
		.exec(http("request")
			.get("/controller/getScore"))

	setUp(scn.inject(
		atOnceUsers(1),
		rampUsers(10000).during(10.seconds)
		)).protocols(httpProtocol)
}