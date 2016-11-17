package interactive.fixtures

import fastparse.all
import fastparse.core.Parsed
import interactive.parser.{InteractiveModeParser, JsonParser}
import interactive.token.InteractiveMode
import interactive.token.InteractiveMode.Statement
import org.scalatest.FlatSpec
import org.scalatest.prop.TableDrivenPropertyChecks

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

    def optionJson(s: String = validJson): Option[InteractiveMode.Payload] = JsonParser.jsonExpr.parse(s) match {
      case s: Parsed.Success[_,_,_] => s match {
        case Parsed.Success(`s`, _) => Option(`s`.asInstanceOf[InteractiveMode.Payload])
        case _ => None
      }
      case _ => None
    }

    val literal_sql = """CREATE TABLE bobby (name VARCHAR(40))"""
  }
  val f = fixture

  def doesParseToA[P](a: String,
                      parsed: P,
                      pr: all.Parser[Product with Serializable]
                      = parser.expr ) = {
    try {
      val Parsed.Success(par, _) = pr.parse(a)
      assertResult(parsed.getClass)(par.getClass)
    } catch {
      case _: MatchError => fail // The parsing failed
    }
  }

  def doesNotParseToA[P](a: String,
                         parsed: P,
                         pr: all.Parser[Product with Serializable]
                         = parser.expr ) = {
    try {
      val Parsed.Success(par, _) = pr.parse(a)
      assert(par.getClass != parsed.getClass)
    } catch {
      case _: MatchError => succeed
    }
  }

}
