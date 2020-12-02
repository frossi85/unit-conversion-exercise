package com.frossi85.utils

import org.scalatest._

class MathUtilsSpec extends FlatSpec with Matchers {
  "truncateAt" should "truncate the double numer to the specified precision" in {

    MathUtils.truncateAt(0.01745329251994399, 14) shouldEqual 0.017453292519943

    MathUtils.truncateAt(0.001745329251994399, 14) shouldEqual 0.0017453292519943

    MathUtils.truncateAt(0.01745329251994399, 14) shouldEqual 0.017453292519943

    MathUtils.truncateAt(0.1745329251994399, 14) shouldEqual 0.17453292519943

    MathUtils.truncateAt(1.745329251994399, 14) shouldEqual 1.7453292519943
  }
}
