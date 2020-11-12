package unit.converter

import unit.converter.services.{Calculator, Converter}

object Hello extends Greeting with App {


  val calc = new Calculator()
  val res = calc.parse("(2 / 4)* 3")

  val double: Double = 0.005555555555555556/60.0

  val calculator = new Calculator()

  val res2: Double = calculator.parse("0.005555555555555556/60.0")

  println(res2)
}

trait Greeting {
  lazy val greeting: String = "hello"
}
