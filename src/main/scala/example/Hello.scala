package example

import example.models.Calculator

object Hello extends Greeting with App {
  println(greeting)

  val calc = new Calculator()

  val res = calc.parse("(2+ 4)* 3")

  println(res)
}

trait Greeting {
  lazy val greeting: String = "hello"
}
