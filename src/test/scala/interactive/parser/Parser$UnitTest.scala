package interactive.parser

import interactive.Statements._
import interactive.Term._
import interactive.fixtures.InteractiveModeParserFixtures

/**
  * Created by bspriggs on 11/13/2016.
  */
class Parser$UnitTest extends InteractiveModeParserFixtures {

  behavior of "interactive.parser.Parser$UnitTest"

  behavior of "_help"

  {
    val p = parser._help

    "_help" should "parse a help interactive.token" in {
      forAll(f.helpRequests) { word: String => doesParseToA(word, Help(None), p) }
    }

    "_help" should "parse a help interactive.token and some query, with whitespace" in {
      forAll(f.helpRequests) { word: String =>
        doesParseToA(word + " another word", Help(Some("another word")), p)
      }
    }
  }

  behavior of "_stop"

  {
    val p = parser._stop

    "_stop" should "parse a stop interactive.token" in {
      forAll(f.stopRequests) { word: String => doesParseToA(word, Stop, p) }
    }
  }


  behavior of "_request"

  {
    val p = parser._request

    "_request" should "parse a request interactive.token" in {
      forAll(f.requests) { word: String => doesParseToA(word, Request(word), p) }
    }

    "_request" should "not parse a non-request interactive.token" in {
      forAll(f.typeSingle) { word: String => doesNotParseToA("n" + word, Request(word), p) }
    }
  }

  // behavior of "_payload"
  // should be a JSON object, tested in JsonParser$UnitTest

  behavior of "_singleType"

  {
    val p = parser._singleType

    "_singleType" should "parse a singular type" in {
      forAll(f.typeSingle) {
        word: String => doesParseToA(word, Type.One(word), p)
          doesParseToA(s"$word report", Type.One(s"$word report"), p)
      }
    }

    "_singleType" should "not parse a plural type" in {
      forAll(f.typeMany) {
        word: String => doesNotParseToA(word, Type.One(word), p)
          doesNotParseToA(s"$word report", Type.One(s"$word report"), p)
      }
    }
  }

  behavior of "_manyType"

  {
    val p = parser._manyType

    "_manyType" should "parse a plural type" in {
      forAll(f.typeMany) {
        word: String => doesParseToA(word, Type.Many(word), p)
          doesParseToA(s"$word reports", Type.Many(s"$word reports"), p)
      }
    }

    "_manyType" should "not parse a singular type" in {
      forAll(f.typeSingle) {
        word: String => doesNotParseToA(word, Type.Many(word), p)
          doesNotParseToA(s"$word reports", Type.Many(s"$word reports"), p)
      }
    }
  }

  behavior of "_object"

  {
    val p = parser.`_object`

    "_object" should "parse a single type and JSON object" in {
      forAll(f.typeSingle) {
        word: String => doesParseToA(word + f.validJson,
          Obj((Type.One(word), Seq(f.parsedJson()))), p)
          doesParseToA(word + " report " + f.validJson,
            Obj((Type.One(s"$word report"), Seq(f.parsedJson()))), p)
      }
    }

    "_object" should "not parse a JSON object without a type" in {
      doesNotParseToA(f.validJson, Obj((Type.One("type"), Seq(f.parsedJson()))), p)
    }

    "_object" should "not parse a type without a JSON object" in {
      forAll(f.typeSingle) {
        word: String => doesNotParseToA(word, Obj((Type.One(word), Seq(f.parsedJson("{}")))), p)
      }
      forAll(f.typeMany) {
        word: String => doesNotParseToA(word, Obj((Type.One(word), Seq(f.parsedJson("{}")))), p)
      }
    }
  }

  behavior of "_superobject"

  {
    val p = parser._superobject

    "_superobject" should "parse a plural type and a payload" in {
      forAll(f.typeMany) { word: String =>
        doesParseToA(word + " " + f.validJson, SuperObj(Type.Many(word), f.optionJson()), p)
        doesParseToA(word + " reports " + f.validJson, SuperObj(Type.Many(word + " reports"), f.optionJson()), p)
      }
    }

    "_superobject" should "parse a universal modifier with a type" in {
      forAll(f.typeMany) { word: String =>
        doesParseToA("all " + word, SuperObj(Type.Many(word), None), p)
        doesParseToA("all " + word + " reports", SuperObj(Type.Many(word + " reports"), None), p)
      }
    }

    "_superobject" should "parse a universal modifier with a type and clarifying JSON" in {
      forAll(f.typeMany) { word: String =>
        doesParseToA("all " + word + " " + f.validJson, SuperObj(Type.Many(word), f.optionJson()), p)
      }
    }

    "_superobject" should "parse a plural type and single JSON object" in {
      forAll(f.typeMany) {
        word: String => doesParseToA(word + f.validJson, SuperObj((Type.Many(word), f.optionJson())), p)
      }
    }


    "_superobject" should "not parse a universal modifier without a type" in {
      doesNotParseToA("all " + f.validJson, SuperObj((Type.Many(""), f.optionJson())), p)
    }
  }

  behavior of "_sql_literal"

  {
    val p = parser._sql_literal

    "_sql_literal" should "parse a SQL request and query" in {
      doesParseToA("SQL " + f.literal_sql, SQL(f.literal_sql), p)
    }

    "_sql_literal" should "not parse a SQL query without a request" in {
      doesNotParseToA(f.literal_sql, SQL(f.literal_sql), p)
    }

    "_sql_literal" should "not parse a SQL request without a query" in {
      doesNotParseToA("SQL", SQL(""), p)
    }

    "_sql_literal" should "be able to take quoted queries" in {
      doesParseToA("SQL '" + f.literal_sql + "'",
        SQL("'" + f.literal_sql + "'"), p)
      doesParseToA("SQL \"" + f.literal_sql + "\"",
        SQL("\"" + f.literal_sql + "\""), p)
      doesParseToA("SQL `" + f.literal_sql + "`",
        SQL("`" + f.literal_sql + "`"), p)
    }
  }


  behavior of "_expression"

  {
    val p = parser._expression

    "_expression" should "parse a stop request" in {
      forAll(f.stopRequests) {
        word: String => doesParseToA(word, Stop, p)
      }
    }

    "_expression" should "parse a help request with any known keyword" in {
      forAll(f.helpRequests) { help: String =>
        forAll(f.requests) { word: String =>
          doesParseToA(help + " " + word, Help(Some(word)), p)
        }
        forAll(f.typeSingle) { word: String =>
          doesParseToA(help + " " + word, Help(Some(word)), p)
        }
        forAll(f.typeMany) { word: String =>
          doesParseToA(help + " " + word, Help(Some(word)), p)
        }
        forAll(f.stopRequests) { word: String =>
          doesParseToA(help + " " + word, Help(Some(word)), p)
        }
        doesParseToA(help + " any", Help(Some("any")), p)
      }
    }

    "_expression" should "parse a request with a singular object" in {
      forAll(f.requests) { word: String =>
        forAll(f.typeSingle) { `type`: String =>
          doesParseToA(word + " " + `type` + f.validJson,
            Mono((Request(word), Obj((Type.One(`type`), Seq(f.parsedJson()))))), p
          )
        }
      }
    }

    "_expression" should "parse a request with a singular implied type and multiple objects" in {
      forAll(f.requests) { word: String =>
        forAll(f.typeSingle) { `type`: String =>
          doesParseToA(word + " " + `type` + f.validJson + f.validJson + f.validJson,
            Mono((Request(word), Obj((Type.One(`type`), Seq(f.parsedJson(), f.parsedJson(), f.parsedJson()))))), p
          )
        }
      }
    }

    "_expression" should "parse a request with a plural type and singular object" in {
      forAll(f.requests) { word: String =>
        forAll(f.typeMany) { `type`: String =>
          doesParseToA(word + " " + `type` + f.validJson,
            Poly(Request(word), SuperObj((Type.Many(`type`), f.optionJson()))), p
          )
        }
      }
    }

    "_expression" should "parse a generic request with a singular object" in {
      forAll(f.requests) { word: String =>
        forAll(f.typeMany) { `type`: String =>
          doesParseToA(word + " all " + `type` + f.validJson,
            Poly(Request(word), SuperObj((Type.Many(`type`), f.optionJson()))), p
          )
        }
      }
    }

    "_expression" should "not parse a generic request with multiple objects" in {
      forAll(f.requests) { word: String =>
        forAll(f.typeMany) { `type`: String =>
          doesNotParseToA(word + " all " + `type` + f.validJson + f.validJson + f.validJson,
            Poly(Request(word), SuperObj((Type.Many(`type`), f.optionJson()))), p
          )
        }
      }
    }
  }

  behavior of "_statements"

  {
    val p = parser._statements

    "_statements" should "parse multiple expressions" in {
      val expected = Seq(
        Help(Some("create")), Mono((Request("create"), Obj(Type.One("user"), Seq(f.parsedJson()))))
      )
      assertResult(expected)(p.parse("help create; create user " + f.validJson + "; stop;"))
    }

    "_statements" should "not parse after a Stop expression" in {

    }

    "_statements" should "not parse multiple expressions delimited by a comma" in {

    }

    "_statements" should "parse multiple expressions delimited by a semicolon" in {

    }
  }

}
