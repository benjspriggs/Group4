package interactive.parser

import interactive.fixtures.InteractiveModeParserFixtures

/**
  * Created by bspriggs on 11/13/2016.
  */

class Parser$SmokeTest extends InteractiveModeParserFixtures {
  import interactive.Tokens._

  behavior of "Parser"

  it must "handle requests to stop the session" in {
    forAll(f.stopRequests) { word: String => doesParseToA(word, Stop) }
  }

  it must "handle requests for help" in {
    forAll(f.helpRequests) { word: String =>
      doesParseToA(word, Help(None))
      forAll(f.typeSingle) { `type`: String =>
        doesParseToA(word ++ " " ++ `type`, Help(Some(`type`)))
      }
    }
  }

  it must "handle generalized statements" in {
    forAll(f.requests) { request: String =>
      forAll(f.typeMany) {
        `type`: String => doesParseToA(
          request ++ " " ++ `type` ++ " " ++ f.validJson,
          (Request(request), SuperObj((Type.Many(`type`), f.optionJson())))
        )
      }
      forAll(f.typeMany) {
        `type`: String => doesParseToA(
          request ++ " all " ++ `type`,
          (Request(request), SuperObj((Type.Many(`type`), f.optionJson(""))))
        )
      }
    }
  }

  it must "handle specific statements" in {
    forAll(f.requests) { request: String =>
      forAll(f.typeSingle) {
        `type`: String => doesParseToA(
          request ++ " " ++ `type` ++ " " ++ f.validJson,
          (Request(request), Obj)
        )
      }
    }
  }

  it must "handle SQL literals" in {
    doesParseToA("SQL " ++ f.literal_sql, SQL(f.literal_sql))
  }

  it must "handle reports" in {
    forAll(f.requests) {
      request: String =>
      forAll(f.typeSingle) {
        t: String => doesParseToA(s"$request report $t" ++ f.validJson,
          (Request(request), Obj((Type.One(t), f.parsedJson()))))
      }
      forAll(f.typeMany) {
        t: String =>
          doesParseToA(s"$request reports $t" ++ f.validJson,
          (Request(request), SuperObj((Type.Many(t), f.optionJson()))))
          doesParseToA(s"$request all reports $t",
            (Request(request), SuperObj((Type.Many(t), f.optionJson()))))
      }
    }
  }
}
