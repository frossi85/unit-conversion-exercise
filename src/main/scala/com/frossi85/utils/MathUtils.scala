package com.frossi85.utils

import java.math.{MathContext, RoundingMode}

object MathUtils {
  def truncateAt(value: Double, precision: Int): Double = {
    val mathContext = new MathContext(precision, RoundingMode.FLOOR)

    BigDecimal(value, mathContext).toDouble
  }
}
