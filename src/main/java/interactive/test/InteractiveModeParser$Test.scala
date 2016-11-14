/**
  * Created by bspriggs on 11/13/2016.
  */

import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.{FlatSpec, Matchers}

class InteractiveModeParser$Test extends FlatSpec with TableDrivenPropertyChecks with Matchers {
  import InteractiveModeParser.InteractiveMode._
  import fastparse.core.Parsed

  val parser = InteractiveModeParser

  val stopRequests = Table( "word",
      "quit",
      "bye",
      "exit",
      ""
    )

  val helpRequests = Table( "word",
      "help",
      "?"
    )

  val requests = Table(
      "name",
      "create",
      "show",
      "update",
      "delete",
      "write"
    )

  val typeSingle = Table(
      "type",
      "user",
      "member",
      "provider",
      "service"
    )

  val typeMany = Table(
      "types",
      "users",
      "members",
      "providers",
      "services"
    )

  val validJson = """
      |{
      | "name": "Kate Abernathy",
      | "age": 23,
      | "member_number": 4
      | }
    """.stripMargin

  def parsesToA[P](a: String, parsed: P) = {
    assert(parser.expr.parse(a) match {
      case Parsed.Success(parsed, _) => true
      case _ => false
    })
  }

  it must "handle requests to stop the session" in {
    forAll(stopRequests) { word: String => parsesToA(word, Stop) }
  }

  it must "handle requests for help" in {
    forAll(helpRequests) { word: String => parsesToA(word, Help) }
  }

  it must "handle generalized requests" in {
    forAll(requests) { request: String =>
      forAll(typeMany) {
        `type`: String => parsesToA(request ++ " " ++ `type` ++ " " ++ validJson, Request)
      }
      forAll(typeMany) {
        `type`: String => parsesToA(request ++ " all " ++ `type`, Request)
      }
    }
  }
}
