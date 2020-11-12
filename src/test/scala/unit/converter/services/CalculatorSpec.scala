package unit.converter.services

import org.scalatest._

class CalculatorSpec extends FlatSpec with Matchers {
  "parse" should "parse the string expression and calculate the numerical result" in {
    val calculator = new Calculator()

    calculator.parse("2/(4*4)/2") shouldEqual 0.0625

    calculator.parse("5*6/2") shouldEqual 15

    calculator.parse("(5*6)/2") shouldEqual 15

    calculator.parse("5*(6/2)") shouldEqual 15

    calculator.parse("((2*3)/1)*(3*2)") shouldEqual 36

    calculator.parse("0.005555555555555556/60.0") shouldEqual 0.005555555555555556/60.0
  }
}
