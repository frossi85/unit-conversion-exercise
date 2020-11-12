package com.frossi85.services

import Converter.{convertToFactorExpression, replaceWithSIUnits}

case class ConversionResult(unitName: String, multiplicationFactor: Double)

class Converter {
  val calculator = new Calculator()

  def toSI(expresion: String): ConversionResult = {
    val withSISymbols = replaceWithSIUnits(expresion)
    val factorExpression = convertToFactorExpression(expresion)

    ConversionResult(
      withSISymbols,
      calculator.parse(factorExpression)
    )
  }
}

object Converter {
  private val unitsConversion = List(
    UnitConversion("minute", "min", "time", "s", 60),
    UnitConversion("hour", "h", "time", "s", 3600),
    UnitConversion("day", "d", "time", "s", 86400),
    UnitConversion("degree", "º", "unitless/plane angle", "rad", 1.0 / 180),
    UnitConversion("arcminute", "'", "unitless/plane angle", "rad", 1.0 / 10800),
    UnitConversion("arcsecond", "''", "unitless/plane angle", "rad", 1.0 / 648000),
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
