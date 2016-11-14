import InteractiveMode._
import fastparse.core.Parsed

/**
  * Created by bspriggs on 11/13/2016.
  */
class InteractiveModeParser$UnitTest extends InteractiveModeParserFixtures {

  behavior of "InteractiveModeParser$UnitTest"

  behavior of "_help"

  {
    "_help" should "parse a help token" in {
      forAll(f.helpRequests) { word: String => parsesToA(word, Help) }
    }

    "_help" should "parse a help token and some query, with whitespace" in {
      forAll(f.helpRequests) { word: String =>
        assert(parser.expr.parse(word + " another word") match {
          case Parsed.Success(Help(Some("another word")), _) => true
          case _ => false
        })
      }
    }

    "_help" should "not parse tokens with missing whitespace" in {
      forAll(f.helpRequests) { word: String => doesNotParseToA(word + "asdfad", Help)}
    }
  }

  behavior of "_stop"

  {
    "_stop" should "parse a stop token" in {
      forAll(f.stopRequests) { word: String => parsesToA(word, Stop)}
    }

    "_stop" should "not parse if there's something after the stop token" in {
      forAll(f.stopRequests) { word: String => doesNotParseToA(word + "asdfa", Stop)}
    }
  }

  behavior of "_superobject"

  {
    "_superobject" should "parse a plural type and a payload" in {
      forAll(f.typeMany) { word: String =>
        parsesToA(word + " " + f.validJson, SuperObj)
      }
    }

    "_superobject" should "parse a universal modifier with a type" in {
      forAll(f.typeMany) { word: String =>
        parsesToA("all " + word, SuperObj)
      }
    }

    "_superobject" should "parse a universal modifier with a type and clarifying JSON" in {
      forAll(f.typeMany) { word: String =>
        parsesToA("all " + word + " " + f.validJson, SuperObj)
      }
    }

    "_superobject" should "not parse a universal modifier without a type" in {
      doesNotParseToA("all " + f.validJson, SuperObj)
    }
  }

  behavior of "_request"

  {
    "_request" should "parse a request token" in {
      forAll(f.requests) { word: String => parsesToA(word, Request) }
    }

    "_request" should "not parse a non-request token" in {
      forAll(f.requests) { word: String => doesNotParseToA(word + "n", Request) }
    }
  }

  behavior of "_payload"
  // should be a JSON object

  it should "_manyType" in {

  }

  it should "_object" in {

  }

  it should "_sql_literal" in {

  }

  it should "_singleType" in {

  }

  it should "_expr" in {

  }

}
