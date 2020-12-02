package com.frossi85

import akka.actor.ActorSystem
import akka.http.scaladsl.model.headers.`Content-Type`
import akka.http.scaladsl.model.{MediaTypes, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.frossi85.WebServer.{complete, get, parameters, path, pathPrefix, serialize}
import com.frossi85.services.{ConversionResult, Converter}

class UnitsRoutes(converter: Converter)(implicit val system: ActorSystem) {
  val routes: Route = get {
    pathPrefix("units") {
      path("si") {
        get {
          parameters(Symbol("units").as[String]) { units =>
            complete(
              StatusCodes.OK,
              List(`Content-Type`(MediaTypes.`application/json`)),
              serialize[ConversionResult](converter.toSI(units))
            )
          }
        }
      }
    }
  }
}

