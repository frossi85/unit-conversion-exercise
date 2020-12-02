package com.frossi85

import akka.actor.ActorSystem
import akka.http.scaladsl.marshalling.{Marshaller, ToEntityMarshaller}
import akka.http.scaladsl.model.ContentTypes._
import akka.http.scaladsl.model.{MediaTypes, StatusCodes}
import akka.http.scaladsl.model.headers.`Content-Type`
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.frossi85.services.{ConversionResult, Converter}
import com.frossi85.utils.Serialization
import scala.util.{Failure, Success, Try}

class UnitsRoutes(converter: Converter)(implicit val system: ActorSystem) extends Serialization {
  implicit val marshaller: ToEntityMarshaller[ConversionResult] =
    Marshaller.StringMarshaller.wrap(MediaTypes.`application/json`)(serialize[ConversionResult](_))

  val routes: Route = get {
    pathPrefix("units") {
      path("si") {
        get {
          parameters(Symbol("units").as[String]) { units =>
            Try(converter.toSI(units)) match {
              case Success(value) => complete(
                StatusCodes.OK,
                List(`Content-Type`(`application/json`)),
                value
              )
              case Failure(_) => complete(
                StatusCodes.BadRequest,
                List(`Content-Type`(`application/json`)),
                "Invalid units parameter"
              )
            }
          }
        }
      }
    }
  }
}

