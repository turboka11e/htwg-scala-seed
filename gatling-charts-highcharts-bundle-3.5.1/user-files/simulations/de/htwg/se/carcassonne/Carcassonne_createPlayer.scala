package de.htwg.se.carcassonne

import scala.concurrent.duration._
import scala.util.Random
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class Carcassonne extends Simulation {

	val httpProtocol = http
		.baseUrl("http://localhost:8080")
		.inferHtmlResources()
		.acceptHeader("*/*")
		.acceptEncodingHeader("gzip, deflate")
		.userAgentHeader("PostmanRuntime/7.28.0")

	val headers_0 = Map("Postman-Token" -> "28bd2e33-605e-49e2-95da-fdd176dc8f8e")

	val headers_1 = Map("Postman-Token" -> "c79b37a4-7a14-4d6b-b259-1fa4da159346")

	val headers_2 = Map("Postman-Token" -> "1eb767ca-4284-4d96-8ebc-062496bcfa56")

	val headers_3 = Map(
		"Content-Type" -> "text/plain",
		"Postman-Token" -> "107bac1d-e597-4d97-9be2-a84d4a51786f")


	val name_generator = Iterator.continually(Map("randstring" -> ( Random.alphanumeric.take(10).mkString )))

	val scn = scenario("Carcassonne")
		.feed(name_generator)
		.exec(http("request")
			.get("/controller/addPlayer/${randstring}")
			.headers(headers_2))

	setUp(scn.inject(
		atOnceUsers(1),
		rampUsers(1000).during(5.seconds)
		)).protocols(httpProtocol)
}