package unit.converter

import unit.converter.services.{Calculator, Converter}

object Hello extends Greeting with App {
  println(greeting)

  val calc = new Calculator()
  val res = calc.parse("(2+ 4)* 3")

  println(res)

  println(
    Converter.convertToFactorExpression("2/(min*4+minute)*º")
  )

  println(
    Converter.replaceWithSIUnits("2/(min*4+minute)*º")
  )

  println(
    Converter.replaceWithSIUnits("2/(min*4+minute ) *º")
  )

  println(
    Converter.replaceWithSIUnits("2/(min*4+º)")
  )

  println(
    Converter.replaceWithSIUnits("2/(min*4+º)*1")
  )

  println(
    Converter.replaceWithSIUnits("2/(º*4+º)*1")
  )

  println(
    Converter.replaceWithSIUnits("degree")
  )

  println(
    Converter.replaceWithSIUnits("degree/minute")
  )

  println(
    Converter.replaceWithSIUnits("(degree/(minute*hectare))")
  )

  println(
    Converter.replaceWithSIUnits("ha*°")
  )

  println(
    Converter.replaceWithSIUnits("ha")
  )
}

trait Greeting {
  lazy val greeting: String = "hello"
}
