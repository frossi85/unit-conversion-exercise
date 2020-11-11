package unit.converter.services

import org.scalatest._

class ConverterSpec extends FlatSpec with Matchers {
  "Converter.convertToFactorExpression" should "replace units with its SI Conversion amount" in {

    Converter.convertToFactorExpression(
      "2/(min*4+minute)*º"
    ) shouldEqual "2/(60.0*4+60.0)*0.005555555555555556"
  }

  "Converter.replaceWithSIUnits" should "replace raw units names with SI counterpart" in {

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
  }
}
