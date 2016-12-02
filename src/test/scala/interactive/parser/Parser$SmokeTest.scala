package interactive.parser

import interactive._
import interactive.Term._
import interactive.fixtures.InteractiveModeParserFixtures

import scala.collection.mutable.ArrayBuffer

/**
  * Created by bspriggs on 11/13/2016.
  */

class Parser$SmokeTest extends InteractiveModeParserFixtures {

  behavior of "Parser"

  it must "handle requests to stop the session" in {
    forAll(f.stopRequests) { word: String => doesParseToA(word, Stop) }
  }

  it must "handle requests for help" in {
    forAll(f.helpRequests) { word: String =>
      doesParseToA(word, Help(None))
      forAll(f.typeSingle) { `type`: String =>
        doesParseToA(word + " " + `type`, Help(Some(`type`)))
      }
    }
  }

  it must "handle generalized statements" in {
    forAll(f.requests) { request: String =>
      forAll(f.typeMany) {
        `type`: String => doesParseToA(
          request + " " + `type` + " " + f.validJson,
          Poly((Request(request), SuperObj((Many(`type`), f.optionJson()))))
        )
      }
      forAll(f.typeMany) {
        `type`: String => doesParseToA(
          request + " all " + `type`,
          Poly((Request(request), SuperObj((Many(`type`), f.optionJson("")))))
        )
      }
    }
  }

  it must "handle specific statements" in {
    forAll(f.requests) { request: String =>
      forAll(f.typeSingle) {
        `type`: String => doesParseToA(
          request + " " + `type` + " " + f.validJson,
          Mono((Request(request), Obj(One(`type`), Seq(f.parsedJson()))))
        )
      }
    }
  }

  it must "handle SQL literals" in {
    doesParseToA("SQL " + f.literal_sql, SQL(f.literal_sql))
  }

  it must "handle reports" in {
    forAll(f.requests) {
      request: String =>
        forAll(f.typeSingle) {
          t: String => doesParseToA(s"$request $t report" + f.validJson,
            Mono(Request(request), Obj((One(t), Seq(f.parsedJson()))))
          )
        }
        forAll(f.typeMany) {
          t: String =>
            doesParseToA(s"$request $t reports" + f.validJson,
              Poly((Request(request), SuperObj((Many(t), f.optionJson())))))
            doesParseToA(s"$request all $t reports",
              Poly((Request(request), SuperObj((Many(t), f.optionJson()))))
            )
        }
    }
  }

  it must "handle multiple statements" in {
    val p = Parser.statements
    forAll(f.statements) {
      statement: String => forAll(f.stopRequests) {
        stop: String => doesParseToA(s"$statement $stop;",
          ArrayBuffer(Parser.non_terminating_statement.parse(statement)), p)
      }
    }
  }
}
