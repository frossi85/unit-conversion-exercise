package unit.converter.utils

import org.json4s.{DefaultFormats, Formats}
import org.json4s.native.JsonMethods._
import org.json4s.native.Serialization._

trait Serialization {
  type From = String
  type To = String

  val formats = DefaultFormats.lossless

  def serialize[T <: AnyRef: Manifest](message: T): String = {
    val serialized = write(message)(DefaultFormats)
    compact(render(parse(serialized).snakizeKeys))
  }
}
