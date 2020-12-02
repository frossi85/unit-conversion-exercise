package com.frossi85.services

import org.scalatest._

class ConverterSpec extends FlatSpec with Matchers {
  "convertToFactorExpression" should "replace units with its SI Conversion amount" in {

    Converter.convertToFactorExpression(
      "2/(min*4+minute)*º"
    ) shouldEqual "2/(60.0*4+60.0)*0.017453292519943295"
  }

  "replaceWithSIUnits" should "replace raw units names with SI counterpart" in {

    Converter.replaceWithSIUnits("2/(min*4+minute)*º") shouldEqual "2/(s*4+s)*rad"

    Converter.replaceWithSIUnits("2/(min*4+minute ) *º") shouldEqual "2/(s*4+s ) *rad"

    Converter.replaceWithSIUnits("2/(min*4+º)") shouldEqual "2/(s*4+rad)"

    Converter.replaceWithSIUnits("2/(min*4+º)*1") shouldEqual "2/(s*4+rad)*1"

    Converter.replaceWithSIUnits("2/(º*4+º)*1") shouldEqual "2/(rad*4+rad)*1"

    Converter.replaceWithSIUnits("degree") shouldEqual "rad"

    Converter.replaceWithSIUnits("degree/minute") shouldEqual "rad/s"

    Converter.replaceWithSIUnits("(degree/(minute*hectare))") shouldEqual "(rad/(s*m^2))"

    Converter.replaceWithSIUnits("ha*°") shouldEqual "m^2*°"

    Converter.replaceWithSIUnits("(degree/(minute*hectare))") shouldEqual "(rad/(s*m^2))"

    Converter.replaceWithSIUnits("bug") shouldEqual "bug"
  }

  "isValid" should "validate the expression" in {

    Converter.isValid("degree/minute") shouldEqual true

    Converter.isValid("(bug)*degree/minute") shouldEqual false

    Converter.isValid("(bug*degree)/minute") shouldEqual false

    Converter.isValid("(bug*(degree * degree))/minute") shouldEqual false
  }

  "toSI" should "return the unit name and mutiplication factor" in {
    val converter = new Converter()

    converter
      .toSI("degree") shouldEqual ConversionResult("rad", 0.017453292519943295)

    converter
      .toSI("(degree/minute)") shouldEqual ConversionResult("(rad/s)", 2.908882086657216E-4)
  }

  "toSI" should "throw a ValidationException on invalid expression" in {
    val converter = new Converter()

    an [ValidationException] should be thrownBy
      converter
        .toSI("(bug*(degree * degree))/minute")
  }
}
