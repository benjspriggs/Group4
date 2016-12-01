package interactive.fixtures

import fastparse.all
import fastparse.core.Parsed
import interactive.InteractiveModeFixtures
import interactive.parser.Parser

/**
  * Created by bspriggs on 11/13/2016.
  */
trait InteractiveModeParserFixtures extends InteractiveModeFixtures {

  val parser = Parser

  def doesParseToA[P](it: String,
                      expected: P,
                      _parser: all.Parser[Any]
                      = parser.expression ) = {
    try {
      val Parsed.Success(actual, _) = _parser.parse(it)
      assertResult(expected.getClass, "Expected: " + expected.getClass.toString)(actual.getClass)
    } catch {
      case me: MatchError =>
        fail(s"While parsing `$it`, encountered error:" + me.getMessage())
    }
  }

  def doesNotParseToA[P](it: String,
                         expected: P,
                         _parser: all.Parser[Any]
                         = parser.expression ) = {
    try {
      val Parsed.Success(actual, _) = _parser.parse(it)
      assert(actual.getClass != expected.getClass,
        actual.getClass.toString + " should not parse to " + expected.getClass.toString)
    } catch {
      case _: MatchError => succeed
    }
  }

}
