package com.frossi85

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.{HttpApp, Route}
import akka.http.scaladsl.settings.ServerSettings
import akka.stream.ActorMaterializer
import com.frossi85.services.Converter
import com.frossi85.utils.Serialization
import com.typesafe.config.ConfigFactory
import spray.json.DefaultJsonProtocol

object WebServer extends HttpApp with SprayJsonSupport with DefaultJsonProtocol with Serialization {
  // needed to run the route
  implicit val system = ActorSystem("unit-conversion-system")
  implicit val materializer = ActorMaterializer()
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.dispatcher

  val converter = new Converter()
  val unitsRoutes = new UnitsRoutes(converter)

  override protected def routes: Route = unitsRoutes.routes

  override protected def postHttpBinding(binding: Http.ServerBinding): Unit = {
    super.postHttpBinding(binding)
    val sys = systemReference.get()
    sys.log.info(s"Running on [${sys.name}] actor system")
  }

  override protected def postHttpBindingFailure(cause: Throwable): Unit = {
    println(s"The server could not be started due to $cause")
  }
}

object UnitConverterServer {
  final def main(args: Array[String]) = {
    WebServer.startServer("0.0.0.0", 8080, ServerSettings(ConfigFactory.load))
  }
}


