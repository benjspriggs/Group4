package interactive.parser

import interactive.fixtures.InteractiveModeParserFixtures

/**
  * Created by bspriggs on 11/13/2016.
  */

class Parser$SmokeTest extends InteractiveModeParserFixtures {
  import interactive.Tokens._

  behavior of "Parser"

  it must "handle requests to stop the session" in {
    forAll(f.stopRequests) { word: String => doesParseToA(s"$word;", Stop) }
  }

  it must "handle requests for help" in {
    forAll(f.helpRequests) { word: String =>
      doesParseToA(s"$word", Help(None))
      forAll(f.typeSingle) { type_s: String =>
        doesParseToA(s"$word $type_s; ", Help(Some(type_s)))
      }
    }
  }

  it must "handle generalized statements" in {
    forAll(f.requests) { request: String =>
      forAll(f.typeMany) {
        typem: String => doesParseToA(
          s"$request $typem " + f.validJson + ";",
          (Request(request), SuperObj((Type.Many(typem), f.optionJson())))
        )
      }
      forAll(f.typeMany) {
        typem: String => doesParseToA(
          s"$request all $typem;",
          (Request(request), SuperObj((Type.Many(typem), f.optionJson(""))))
        )
      }
    }
  }

  it must "handle specific statements" in {
    forAll(f.requests) { request: String =>
      forAll(f.typeSingle) {
        type_s: String => doesParseToA(
          s"$request $type_s " + f.validJson,
          (Request(request), Obj)
        )
      }
    }
  }

  it must "handle SQL literals" in {
    doesParseToA("SQL " + f.literal_sql + ";", SQL(f.literal_sql))
  }

  it must "handle reports" in {
    forAll(f.requests) {
      request: String =>
      forAll(f.typeSingle) {
        t: String => doesParseToA(s"$request $t report;" + f.validJson,
          (Request(request), Obj((Type.One(t), f.parsedJson()))))
      }
      forAll(f.typeMany) {
        t: String =>
          doesParseToA(s"$request $t reports;" + f.validJson,
          (Request(request), SuperObj((Type.Many(t), f.optionJson()))))
          doesParseToA(s"$request all $t reports;",
            (Request(request), SuperObj((Type.Many(t), f.optionJson()))))
      }
    }
  }
}
