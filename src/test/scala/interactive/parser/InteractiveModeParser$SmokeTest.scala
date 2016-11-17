package interactive.parser

import interactive.fixtures.InteractiveModeParserFixtures
import interactive.token.InteractiveMode

/**
  * Created by bspriggs on 11/13/2016.
  */

class InteractiveModeParser$SmokeTest extends InteractiveModeParserFixtures {
  import interactive.token.InteractiveMode._

  behavior of "InteractiveModeParser"

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
          (Request(request), SuperObj((InteractiveMode.Type.Many(`type`), f.optionJson())))
        )
      }
      forAll(f.typeMany) {
        `type`: String => doesParseToA(
          request ++ " all " ++ `type`,
          (Request(request), SuperObj((InteractiveMode.Type.Many(`type`), f.optionJson(""))))
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
}
