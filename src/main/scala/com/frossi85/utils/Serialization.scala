package com.frossi85.utils

import akka.http.scaladsl.coding.{Decoder, Encoder}
import org.json4s.DefaultFormats
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