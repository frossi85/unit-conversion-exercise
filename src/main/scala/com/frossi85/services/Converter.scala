package com.frossi85.services

import Converter.{convertToFactorExpression, isValid, replaceWithSIUnits}

case class ConversionResult(unitName: String, multiplicationFactor: Double)

class Converter {
  val calculator = new Calculator()

  def toSI(expresion: String): ConversionResult = {
    if(!isValid(expresion)) {
      throw ValidationException("Invalid symbols in the expression")
    }

    val withSISymbols = replaceWithSIUnits(expresion)
    val factorExpression = convertToFactorExpression(expresion)

    ConversionResult(
      withSISymbols,
      calculator.parse(factorExpression)
    )
  }
}

case class ValidationException(message: String) extends Exception(message)

object Converter {
  private val unitsConversionMap = Map(
    "minute" -> UnitConversion("minute", "min", "time", "s", 60),
    "hour" -> UnitConversion("hour", "h", "time", "s", 3600),
    "day" -> UnitConversion("day", "d", "time", "s", 86400),
    "degree" -> UnitConversion("degree", "º", "unitless/plane angle", "rad", Math.PI / 180),
    "arcminute" -> UnitConversion("arcminute", "'", "unitless/plane angle", "rad", Math.PI / 10800),
    "arcsecond" -> UnitConversion("arcsecond", "''", "unitless/plane angle", "rad", Math.PI / 648000),
    "hectare" -> UnitConversion("hectare", "ha", "area", "m^2", 10000),
    "hectare" -> UnitConversion("hectare", "L", "volume", "m^3", 0.001),
    "tonne" -> UnitConversion("tonne", "t", "mass", "kg", 1000)
  )

  private val unitsConversion = List(
    UnitConversion("minute", "min", "time", "s", 60),
    UnitConversion("hour", "h", "time", "s", 3600),
    UnitConversion("day", "d", "time", "s", 86400),
    UnitConversion("degree", "º", "unitless/plane angle", "rad", Math.PI / 180),
    UnitConversion("arcminute", "'", "unitless/plane angle", "rad", Math.PI / 10800),
    UnitConversion("arcsecond", "''", "unitless/plane angle", "rad", Math.PI / 648000),
    UnitConversion("hectare", "ha", "area", "m^2", 10000),
    UnitConversion("litre", "L", "volume", "m^3", 0.001),
    UnitConversion("tonne", "t", "mass", "kg", 1000)
  )

  def convertToFactorExpression(expression: String): String = {
    unitsConversion
      .foldLeft(expression)((newExpression: String, conversion: UnitConversion) =>
        newExpression
          .replaceAll(
            s"\\b${conversion.name}\\b|\\b${conversion.symbol}\\b",
            conversion.siFactorConversion.toString
          )
      )
  }

  def isValid(expression: String): Boolean = {
    val isInvalid = expression.replaceAll(
      "/|\\*|\\(|\\)|\\s",
      ","
    )
      .split(',')
      .exists(x => x.isBlank == false && !unitsConversionMap.isDefinedAt(x))

    !isInvalid
  }

  def replaceWithSIUnits(expression: String): String = {
    unitsConversion
      .foldLeft(expression)((newExpression: String, conversion: UnitConversion) =>
        newExpression
          .replaceAll(
            s"\\b${conversion.name}\\b|\\b${conversion.symbol}\\b",
            conversion.siSymbol
          )
      )
  }
}
