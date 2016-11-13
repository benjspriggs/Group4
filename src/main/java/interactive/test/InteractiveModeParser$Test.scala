/**
  * Created by bspriggs on 11/13/2016.
  */

import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.{FlatSpec, Matchers, PropSpec}

class InteractiveModeParser$Test extends FlatSpec with TableDrivenPropertyChecks with Matchers {
  import fastparse.core.Parsed
  import InteractiveModeParser.InteractiveMode.Stop

  val parser = InteractiveModeParser

  val stopRequests =
    Table(
      "word",
      "quit",
      "bye",
      ""
    )

  val validJson =
    """
      |{
      | "name": "Kate Abernathy",
      | "age": 23,
      | "member_number": 4
      | }
    """.stripMargin

  it should "handle requests to stop the session" in {
    forAll(stopRequests) { word: String =>
      assert(parser.expr.parse(word) match {
        case Parsed.Success(Stop, _) => true
        case _ => false })
    }
  }
}
