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

  it should "_superobject" in {

  }

  it should "_request" in {

  }

  it should "_payload" in {

  }

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
