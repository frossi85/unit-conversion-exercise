package unit.converter.services

import scala.util.parsing.combinator.PackratParsers
import scala.util.parsing.combinator.lexical.StdLexical
import scala.util.parsing.combinator.syntactical.StdTokenParsers

class Calculator extends StdTokenParsers with PackratParsers {
  override type Tokens = StdLexical

  override val lexical = new StdLexical

  lexical.delimiters ++= List("(", ")", "+", "-", "*", "/")

  lazy val expr: PackratParser[Double] = mulDiv

  lazy val mulDiv: PackratParser[Double] = term * (
    "*" ^^^ { (left: Double, right: Double) => left * right }
    | "/" ^^^ { (left: Double, right: Double) => left / right } )

  lazy val term: PackratParser[Double] = "(" ~> expr <~ ")" | numericLit ^^ (_.toDouble)

  def parse(str: String) = expr(new PackratReader(new lexical.Scanner(str))) match {
    case Success(result, remain) if remain.atEnd => result
    case Success(_, remain) => throw new RuntimeException(s"Unparsed input at ${remain.pos}")
    case NoSuccess(msg, remain) => throw new RuntimeException(s"Parse error $msg at ${remain.pos}")
  }
}



case class UnitConversion(
                           name: String,
                           symbol: String,
                           quantity: String,
                           siSymbol: String,
                           siFactorConversion: Double
                         )

