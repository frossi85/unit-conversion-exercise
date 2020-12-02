import akka.actor.testkit.typed.scaladsl.ActorTestKit
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.frossi85.UnitsRoutes
import com.frossi85.services.Converter
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{Matchers, WordSpec}

class UnitsRoutesSpec extends WordSpec with Matchers with ScalaFutures with ScalatestRouteTest {
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
    "return degree converted unit (GET /units?=degree)" in {
      // note that there's no need for the host part in the uri:
      val request = HttpRequest(uri = "/units?=degree")

      request ~> routes ~> check {
        status should ===(StatusCodes.OK)

        // we expect the response to be json:
        contentType should ===(ContentTypes.`application/json`)

        // and no entries should be in the list:
        entityAs[String] should ===("""{"users":[]}""")
      }
    }
}
