/**
  * Created by bspriggs on 11/13/2016.
  */

import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.{FlatSpec, Matchers}

class InteractiveModeParser$Test extends FlatSpec with TableDrivenPropertyChecks with Matchers {
  import InteractiveModeParser.InteractiveMode._
  import fastparse.core.Parsed

  val parser = InteractiveModeParser

  val stopRequests =
    Table(
      "word",
      "quit",
      "bye",
      ""
    )

  val helpRequests =
    Table(
      "help",
      "?"
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

  it should "handle requests for help" in {
    forAll(helpRequests) { word: String =>
      assert(parser.expr.parse(word) match {
        case Parsed.Success(Help(_), _) => true
        case _ => false })
    }
  }
}
