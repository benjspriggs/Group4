/**
  * Created by bspriggs on 11/13/2016.
  */

import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.{Matchers, PropSpec}

class InteractiveModeParser$Test extends PropSpec with TableDrivenPropertyChecks with Matchers {
  import fastparse.core.Parsed
  import InteractiveModeParser.InteractiveMode.Stop

  val parser = InteractiveModeParser

  val stopRequests =
    Table(
      ("word", "n"),
      ("quit", 4),
      ("bye", 3),
      ("", 0)
    )

  property("Empty input is a Stop request") {
    forAll(stopRequests) { (word: String, n: Int) =>
      assert(parser.expr.parse(word) == Parsed.Success(Stop, n))
    }
  }
}
