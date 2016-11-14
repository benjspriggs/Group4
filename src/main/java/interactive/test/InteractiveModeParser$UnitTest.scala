import InteractiveMode._

/**
  * Created by bspriggs on 11/13/2016.
  */
class InteractiveModeParser$UnitTest extends InteractiveModeParserFixtures {

  behavior of "InteractiveModeParser$UnitTest"

  behavior of "_help"

  {
    "_help" should "parse a help token" in {
      forAll(f.helpRequests) { word: String => doesParseToA(word, Help, parser._help) }
    }

    "_help" should "parse a help token and some query, with whitespace" in {
      forAll(f.helpRequests) { word: String =>
        doesParseToA(word + " another word", Help, parser._help)
      }
    }

    "_help" should "not parse tokens with missing whitespace" in {
      forAll(f.helpRequests) { word: String => doesNotParseToA(word + "asdfad", Help, parser._help)}
    }
  }

  behavior of "_stop"

  {
    "_stop" should "parse a stop token" in {
      forAll(f.stopRequests) { word: String => doesParseToA(word, Stop, parser._stop)}
    }

    "_stop" should "not parse if there's something after the stop token" in {
      forAll(f.stopRequests) { word: String => doesNotParseToA(word + "asdfa", Stop, parser._stop)}
    }
  }

  behavior of "_superobject"

  {
    "_superobject" should "parse a plural type and a payload" in {
      forAll(f.typeMany) { word: String =>
        doesParseToA(word + " " + f.validJson, SuperObj, parser._superobject)
      }
    }

    "_superobject" should "parse a universal modifier with a type" in {
      forAll(f.typeMany) { word: String =>
        doesParseToA("all " + word, SuperObj, parser._superobject)
      }
    }

    "_superobject" should "parse a universal modifier with a type and clarifying JSON" in {
      forAll(f.typeMany) { word: String =>
        doesParseToA("all " + word + " " + f.validJson, SuperObj, parser._superobject)
      }
    }

    "_superobject" should "not parse a universal modifier without a type" in {
      doesNotParseToA("all " + f.validJson, SuperObj, parser._superobject)
    }
  }

  behavior of "_request"

  {
    "_request" should "parse a request token" in {
      forAll(f.requests) { word: String => doesParseToA(word, Request, parser._request) }
    }

    "_request" should "not parse a non-request token" in {
      forAll(f.typeSingle) { word: String => doesNotParseToA(word + "n", Request, parser._request) }
    }
  }

  // behavior of "_payload"
  // should be a JSON object, tested in JsonParser$UnitTest

  behavior of "_singleType"

  {
    "_singleType" should "parse a singular type" in {
      forAll(f.typeSingle) {
        word: String => doesParseToA(word, Type.One, parser._singleType) }
    }

    "_singleType" should "not parse a plural type" in {
      forAll(f.typeMany) {
        word: String => doesNotParseToA(word, Type.One, parser._singleType) }
    }
  }

  behavior of "_manyType"

  {
    "_manyType" should "parse a plural type" in {
      forAll(f.typeMany) {
        word: String => doesParseToA(word, Type.Many, parser._manyType) }
    }

    "_manyType" should "not parse a singular type" in {
      forAll(f.typeSingle) {
        word: String => doesNotParseToA(word, Type.Many, parser._manyType) }
    }
  }

  behavior of "_object"

  {
    "_object" should "parse a single type and JSON object" in {
      forAll(f.typeSingle) {
        word: String => doesParseToA(word + f.validJson, Obj, parser.`_object`)
      }

      "_object" should "parse a plural type and single JSON object" in {
        forAll(f.typeMany) {
          word: String => doesParseToA(word + f.validJson, Obj, parser.`_object`)
        }
      }

      "_object" should "parse a plural type and multiple JSON objects" in {
        forAll(f.typeMany) {
          word: String => doesParseToA(word + f.validJson + f.validJson, Obj, parser.`_object`)
        }
      }

      "_object" should "not parse a JSON object without a type" in {
        doesNotParseToA(f.validJson, Obj, parser.`_object`)
      }
    }
  }

  behavior of "_sql_literal"


  behavior of "_expr"

}
