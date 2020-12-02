import akka.actor.testkit.typed.scaladsl.ActorTestKit
import akka.http.scaladsl.model._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.frossi85.UnitsRoutes
import com.frossi85.services.Converter
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{Matchers, WordSpec}
import com.frossi85.utils._
import akka.http.scaladsl.server._

class UnitsRoutesSpec extends WordSpec with Matchers with ScalaFutures with ScalatestRouteTest with Serialization {
  lazy val testKit = ActorTestKit()
  implicit def typedSystem = testKit.system
  override def createActorSystem(): akka.actor.ActorSystem =
    testKit.system.classicSystem

  val converter = new Converter()
  lazy val routes = new UnitsRoutes(converter).routes

  "UnitsRoutes" should {

    "return degree converted unit (GET /units/si?units=degree)" in {
      // note that there's no need for the host part in the uri:
      val request = HttpRequest(uri = "/units/si?units=degree")
      val jsonResult = """{
                         |   "unit_name": "rad",
                         |   "multiplication_factor": 0.017453292519943
                         |}""".stripMargin.replaceAll("\\s", "")

      request ~> Route.seal(routes) ~> check {
        status should ===(StatusCodes.OK)

        contentType should ===(ContentTypes.`application/json`)

        entityAs[String] should ===(jsonResult)
      }
    }

    "return NotFound (404) on invalid route request (GET /invalid)" in {
      val request = HttpRequest(uri = "/invalid")

      request ~> Route.seal(routes) ~> check {
        status should ===(StatusCodes.NotFound)
      }
    }

    "return NotFound (404) on missing required parameter units (GET /units/si)" in {
      val request = HttpRequest(uri = "/units/si")

      request ~> Route.seal(routes) ~> check {
        status should ===(StatusCodes.NotFound)
      }
    }

    "return BadRequest (400) on invalid units parameter (GET /units/si?units=)" in {
      val request = HttpRequest(uri = "/units/si?units=")

      request ~> Route.seal(routes) ~> check {
        status should ===(StatusCodes.BadRequest)
      }

      val request2 = HttpRequest(uri = "/units/si?units=bad")

      request2 ~> Route.seal(routes) ~> check {
        status should ===(StatusCodes.BadRequest)
      }
    }
  }
}