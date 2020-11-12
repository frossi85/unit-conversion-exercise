package unit.converter

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.{ContentType, HttpResponse, MediaTypes, StatusCodes}
import akka.http.scaladsl.model.headers.`Content-Type`
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import spray.json.DefaultJsonProtocol
import unit.converter.services.{ConversionResult, Converter}
import unit.converter.utils.{Serialization => CustomSerialization}

import scala.io.StdIn

object UnitConverterServer extends SprayJsonSupport with DefaultJsonProtocol with CustomSerialization {

  // needed to run the route
  implicit val system = ActorSystem("unit-conversion-system")
  implicit val materializer = ActorMaterializer()
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.dispatcher

  val converter = new Converter()

  // formats for unmarshalling and marshalling
  implicit val resultFormat = jsonFormat2(ConversionResult)

  def main(args: Array[String]): Unit = {
    val route: Route = get {
      pathPrefix("units") {
        path("si") {
          get {
            parameters('units.as[String]) { units =>
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

    val bindingFuture = Http().newServerAt("localhost", 8080).bind(route)
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}