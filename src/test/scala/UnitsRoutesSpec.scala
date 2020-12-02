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
  // the Akka HTTP route testkit does not yet support a typed actor system (https://github.com/akka/akka-http/issues/2036)
  // so we have to adapt for now
  lazy val testKit = ActorTestKit()
  implicit def typedSystem = testKit.system
  override def createActorSystem(): akka.actor.ActorSystem =
    testKit.system.classicSystem

  // Here we need to implement all the abstract members of UserRoutes.
  // We use the real UserRegistryActor to test it while we hit the Routes,
  // but we could "mock" it by implementing it in-place or by using a TestProbe
  // created with testKit.createTestProbe()
  val converter = new Converter()
  lazy val routes = new UnitsRoutes(converter).routes

  "UnitsRoutes" should {
    //TODO: Add validations and return proper responses if units param is missing and 404 if other route is requested

    "return degree converted unit (GET /units/si?units=degree)" in {
      // note that there's no need for the host part in the uri:
      val request = HttpRequest(uri = "/units/si?units=degree")
      val jsonResult = """{
                         |   "unit_name": "rad",
                         |   "multiplication_factor": 0.017453292519943
                         |}""".stripMargin.replaceAll("\\s", "")

      request ~> Route.seal(routes) ~> check {
        status should ===(StatusCodes.OK)

        // we expect the response to be json:
        contentType should ===(ContentTypes.`application/json`)



        // and no entries should be in the list:
        //It gives me multiplication_factor: 0.017453292519943295
        entityAs[String] should ===(jsonResult)
      }
    }

    "return NotFound (404) on invalid route request (GET /invalid)" in {
      // note that there's no need for the host part in the uri:
      val request = HttpRequest(uri = "/invalid")

      request ~> Route.seal(routes) ~> check {
        status should ===(StatusCodes.NotFound)
      }
    }
  }
}