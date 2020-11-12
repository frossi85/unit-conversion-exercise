package unit.converter.services

import scala.util.parsing.combinator.{RegexParsers}

class Calculator extends RegexParsers {
  def number: Parser[Double] = """-?(\d+(\.\d*)?|\d*\.\d+)([eE][+-]?\d+)?[fFdD]?""".r ^^ { _.toDouble }
  def factor: Parser[Double] = number | "(" ~> expr <~ ")"
  def expr: Parser[Double] = factor ~ rep( "*" ~ factor | "/" ~ factor) ^^ {
    case number ~ list => (number /: list) {
      case (x, "*" ~ y) => x * y
      case (x, "/" ~ y) => x / y
    }
  }

  def parse(input: String): Double = parseAll(expr, input) match {
    case Success(result, _) => result
    case failure : NoSuccess => scala.sys.error(failure.msg)
  }
}


case class UnitConversion(
                           name: String,
                           symbol: String,
                           quantity: String,
                           siSymbol: String,
                           siFactorConversion: Double
                         )

