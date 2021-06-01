package de.htwg.se

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class CarcassonneGameBuildUp extends Simulation {

	val httpProtocol = http
		.baseUrl("http://localhost:8080")
		.inferHtmlResources()
		.acceptHeader("*/*")
		.acceptEncodingHeader("gzip, deflate")
		.userAgentHeader("PostmanRuntime/7.28.0")

	val headers_0 = Map("Postman-Token" -> "0b812449-a196-41f5-b098-c05554d42f5e")

	val headers_1 = Map("Postman-Token" -> "5d964d27-4323-4dbf-a12b-94df8a802ac9")

	val headers_2 = Map("Postman-Token" -> "d1e05b80-9653-4dd5-b0d1-aa79cd1d9ebc")

	val headers_3 = Map("Postman-Token" -> "cb2b8304-ea73-45ed-92a8-053eb314baee")

	val headers_4 = Map(
		"Content-Type" -> "text/plain",
		"Postman-Token" -> "ff6cd29d-9e44-4af5-8ae4-0bfc59caf448")

	val headers_5 = Map("Postman-Token" -> "c6d7ecd2-c611-4c01-b2fb-1923269262b9")

	val headers_6 = Map("Postman-Token" -> "65de4a48-e3c4-49aa-9081-6a2a0c9ff734")

	val headers_7 = Map("Postman-Token" -> "480be841-be37-4f47-a0ee-6fab0c947112")

	val headers_8 = Map("Postman-Token" -> "e8d0bdf9-11d1-4fd5-96ea-418aa18ab103")

	val headers_9 = Map(
		"Content-Type" -> "text/plain",
		"Postman-Token" -> "48cc6c90-3e09-412b-855f-699b6754e794")

	val headers_10 = Map("Postman-Token" -> "e05fedab-6587-4f26-89c1-65aded67433e")



	val scn = scenario("CarcassonneGameBuildUp")
		.exec(http("newGame")
			.get("/controller/newGame")
			.headers(headers_0))
		.pause(3)
		.exec(http("createGrid")
			.get("/controller/createGrid/8")
			.headers(headers_1))
		.pause(2)
		.exec(http("addPlayer_Lukas")
			.get("/controller/addPlayer/Lukas")
			.headers(headers_2))
		.pause(2)
		.exec(http("addPlayer_Nail")
			.get("/controller/addPlayer/Nail")
			.headers(headers_3))
		.pause(3)
		.exec(http("firstCard")
			.get("/controller/firstCard")
			.headers(headers_4)
			.body(RawFileBody("de/htwg/se/carcassonnegamebuildup/0004_request.txt")))
		.pause(2)
		.exec(http("request_5")
			.get("/controller/newGame")
			.headers(headers_5))
		.pause(3)
		.exec(http("request_6")
			.get("/controller/createGrid/8")
			.headers(headers_6))
		.pause(2)
		.exec(http("request_7")
			.get("/controller/addPlayer/Lukas")
			.headers(headers_7))
		.pause(2)
		.exec(http("request_8")
			.get("/controller/addPlayer/Nail")
			.headers(headers_8))
		.pause(2)
		.exec(http("request_9")
			.get("/controller/firstCard")
			.headers(headers_9)
			.body(RawFileBody("de/htwg/se/carcassonnegamebuildup/0009_request.txt")))
		.pause(2)
		.exec(http("request_10")
			.get("/controller/newGame")
			.headers(headers_10))

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}