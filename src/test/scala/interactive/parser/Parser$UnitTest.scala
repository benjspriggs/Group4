package interactive.parser

import interactive.Statements._
import interactive.Term._
import interactive.fixtures.InteractiveModeParserFixtures

import scala.collection.mutable.ArrayBuffer

/**
  * Created by bspriggs on 11/13/2016.
  */
class Parser$UnitTest extends InteractiveModeParserFixtures {

  behavior of "interactive.parser.Parser$UnitTest"

  behavior of "help"

  {
    val p = parser.help

    "help" should "parse a help interactive.token" in {
      forAll(f.helpRequests) { word: String => doesParseToA(word, Help(None), p) }
    }

    "help" should "parse a help interactive.token and some query, with whitespace" in {
      forAll(f.helpRequests) { word: String =>
        doesParseToA(word + " another word", Help(Some("another word")), p)
      }
    }
  }

  behavior of "stop"

  {
    val p = parser.stop

    "stop" should "parse a stop interactive.token" in {
      forAll(f.stopRequests) { word: String => doesParseToA(word, Stop, p) }
    }
  }


  behavior of "request"

  {
    val p = parser.request

    "request" should "parse a request interactive.token" in {
      forAll(f.requests) { word: String => doesParseToA(word, Request(word), p) }
    }

    "request" should "not parse a non-request interactive.token" in {
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

  behavior of "object"

  {
    val p = parser.`object`

    "object" should "parse a single type and JSON object" in {
      forAll(f.typeSingle) {
        word: String => doesParseToA(word + f.validJson,
          Obj((Type.One(word), Seq(f.parsedJson()))), p)
          doesParseToA(word + " report " + f.validJson,
            Obj((Type.One(s"$word report"), Seq(f.parsedJson()))), p)
      }
    }

    "object" should "not parse a JSON object without a type" in {
      doesNotParseToA(f.validJson, Obj((Type.One("type"), Seq(f.parsedJson()))), p)
    }

    "object" should "not parse a type without a JSON object" in {
      forAll(f.typeSingle) {
        word: String => doesNotParseToA(word, Obj((Type.One(word), Seq(f.parsedJson("{}")))), p)
      }
      forAll(f.typeMany) {
        word: String => doesNotParseToA(word, Obj((Type.One(word), Seq(f.parsedJson("{}")))), p)
      }
    }
  }

  behavior of "superobject"

  {
    val p = parser.superobject

    "superobject" should "parse a plural type and a payload" in {
      forAll(f.typeMany) { word: String =>
        doesParseToA(word + " " + f.validJson, SuperObj(Type.Many(word), f.optionJson()), p)
        doesParseToA(word + " reports " + f.validJson, SuperObj(Type.Many(word + " reports"), f.optionJson()), p)
      }
    }

    "superobject" should "parse a universal modifier with a type" in {
      forAll(f.typeMany) { word: String =>
        doesParseToA("all " + word, SuperObj(Type.Many(word), None), p)
        doesParseToA("all " + word + " reports", SuperObj(Type.Many(word + " reports"), None), p)
      }
    }

    "superobject" should "parse a universal modifier with a type and clarifying JSON" in {
      forAll(f.typeMany) { word: String =>
        doesParseToA("all " + word + " " + f.validJson, SuperObj(Type.Many(word), f.optionJson()), p)
      }
    }

    "superobject" should "parse a plural type and single JSON object" in {
      forAll(f.typeMany) {
        word: String => doesParseToA(word + f.validJson, SuperObj((Type.Many(word), f.optionJson())), p)
      }
    }


    "superobject" should "not parse a universal modifier without a type" in {
      doesNotParseToA("all " + f.validJson, SuperObj((Type.Many(""), f.optionJson())), p)
    }
  }

  behavior of "sql_literal"

  {
    val p = parser.sql_literal

    "sql_literal" should "parse a SQL request and query" in {
      doesParseToA("SQL " + f.literal_sql, SQL(f.literal_sql), p)
    }

    "sql_literal" should "not parse a SQL query without a request" in {
      doesNotParseToA(f.literal_sql, SQL(f.literal_sql), p)
    }

    "sql_literal" should "not parse a SQL request without a query" in {
      doesNotParseToA("SQL", SQL(""), p)
    }

    "sql_literal" should "be able to take quoted queries" in {
      doesParseToA("SQL '" + f.literal_sql + "'",
        SQL("'" + f.literal_sql + "'"), p)
      doesParseToA("SQL \"" + f.literal_sql + "\"",
        SQL("\"" + f.literal_sql + "\""), p)
      doesParseToA("SQL `" + f.literal_sql + "`",
        SQL("`" + f.literal_sql + "`"), p)
    }
  }


  behavior of "non_terminating_statement"

  {
    val p = parser.non_terminating_statement

    "non_terminating_statement" should "not parse a stop request" in {
      forAll(f.stopRequests) {
        word: String => doesNotParseToA(s"$word;", Stop, p)
      }
    }

    "non_terminating_statement" should "parse a help request with any known keyword" in {
      forAll(f.helpRequests) { help: String =>
        forAll(f.requests) { word: String =>
          doesParseToA(s"$help $word;", Help(Some(word)), p)
        }
        forAll(f.typeSingle) { word: String =>
          doesParseToA(s"$help $word;", Help(Some(word)), p)
        }
        forAll(f.typeMany) { word: String =>
          doesParseToA(s"$help $word;", Help(Some(word)), p)
        }
        forAll(f.stopRequests) { word: String =>
          doesParseToA(s"$help $word;", Help(Some(word)), p)
        }
        doesParseToA(s"$help any;", Help(Some("any")), p)
      }
    }

    "non_terminating_statement" should "parse a request with a singular object" in {
      forAll(f.requests) { word: String =>
        forAll(f.typeSingle) { `type`: String =>
          doesParseToA(word + " " + `type` + f.validJson + ";",
            Mono((Request(word), Obj((Type.One(`type`), Seq(f.parsedJson()))))), p
          )
        }
      }
    }

    "non_terminating_statement" should "parse a request with a singular implied type and multiple objects" in {
      forAll(f.requests) { word: String =>
        forAll(f.typeSingle) { `type`: String =>
          doesParseToA(word + " " + `type` + f.validJson + f.validJson + f.validJson + ";",
            Mono((Request(word), Obj((Type.One(`type`), Seq(f.parsedJson(), f.parsedJson(), f.parsedJson()))))), p
          )
        }
      }
    }

    "non_terminating_statement" should "parse a request with a plural type and singular object" in {
      forAll(f.requests) { word: String =>
        forAll(f.typeMany) { `type`: String =>
          doesParseToA(word + " " + `type` + f.validJson + ";",
            Poly(Request(word), SuperObj((Type.Many(`type`), f.optionJson()))), p
          )
        }
      }
    }

    "non_terminating_statement" should "parse a generic request with a singular object" in {
      forAll(f.requests) { word: String =>
        forAll(f.typeMany) { `type`: String =>
          doesParseToA(word + " all " + `type` + f.validJson + ";",
            Poly(Request(word), SuperObj((Type.Many(`type`), f.optionJson()))), p
          )
        }
      }
    }

    "non_terminating_statement" should "not parse a generic request with multiple objects" in {
      forAll(f.requests) { word: String =>
        forAll(f.typeMany) { `type`: String =>
          doesNotParseToA(word + " all " + `type` + f.validJson + f.validJson + f.validJson + ";",
            Poly(Request(word), SuperObj((Type.Many(`type`), f.optionJson()))), p
          )
        }
      }
    }
  }

  behavior of "statements"

  {
    val p = parser.statements
    val expected = ArrayBuffer(
      Help(Some("create")), Mono((Request("create"), Obj(Type.One("user"), ArrayBuffer(f.parsedJson()))))
    ) // "help create; create user $f.validJson; quit;"

    "statements" should "parse multiple expressions" in {
      forAll(f.stopRequests) {
        stop: String =>
          doesParseToA("help create; create user " + f.validJson + s"; $stop;", expected, p)
      }
    }

    "statements" should "not parse after a Stop expression" in {
      forAll(f.stopRequests) {
        stop: String =>
          doesNotParseToA(s"help create; $stop; create user " + f.validJson + s"; $stop;", expected, p)
      }
    }

    "statements" should "not parse multiple expressions delimited by a comma" in {
      forAll(f.stopRequests) {
        stop: String =>
          doesNotParseToA("help create, create user " + f.validJson + s", $stop,", expected, p)
      }
    }
  }

}
