/**
  * Created by bspriggs on 11/13/2016.
  */

import fastparse.core.Parsed.Success
import org.scalatest.{FunSuite, Matchers}

class InteractiveModeParser$Test extends FunSuite with Matchers {
  import fastparse.all._
  import InteractiveModeParser.InteractiveMode._

  val parser = InteractiveModeParser

  test("parse an stop request") {
    val result = parser.expr.parse("quit")
  }
}
