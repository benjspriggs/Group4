package interactive.fixtures

import fastparse.all
import fastparse.core.Parsed
import org.scalatest.FlatSpec
import org.scalatest.prop.TableDrivenPropertyChecks
import interactive.parser.InteractiveModeParser

/**
  * Created by bspriggs on 11/13/2016.
  */
trait InteractiveModeParserFixtures extends FlatSpec with TableDrivenPropertyChecks {

  val parser = InteractiveModeParser

  def fixture = new {
    val helpRequests = Table( "word",
      "help",
      "?"
    )

    val requests = Table(
      "name",
      "create",
      "show",
      "update",
      "delete",
      "write"
    )

    val typeSingle = Table(
      "type",
      "user",
      "member",
      "provider",
      "service"
    )

    val typeMany = Table(
      "types",
      "users",
      "members",
      "providers",
      "services"
    )

    val stopRequests = Table( "word",
      "quit",
      "bye",
      "exit",
      ""
    )

    val validJson = """
                      |{
                      | "name": "Kate Abernathy",
                      | "age": 23,
                      | "member_number": 4
                      | }
                    """.stripMargin

    val literal_sql = """CREATE TABLE bobby (name VARCHAR(40))"""
  }
  val f = fixture

  def parses[P](a: String,
                parsed: P,
                pr: all.Parser[Product with Serializable]
                = parser.expr ) = {
    pr.parse(a) match {
      case s: Parsed.Success[Product with Serializable, Char, String] => s match {
        case Parsed.Success(`parsed`, _) => true
        case _ => false
      }
      case _ => false
    }
  }


  def doesParseToA[P](a: String,
                      parsed: P,
                      pr: all.Parser[Product with Serializable]
                      = parser.expr ) = {
    assert(parses(a, parsed, pr))
  }

  def doesNotParseToA[P](a: String,
                         parsed: P,
                         pr: all.Parser[Product with Serializable]
                         = parser.expr ) = {
    assertResult(false)(parses(a, parsed, pr))
  }

}
