package interactive.fixtures

import fastparse.all
import fastparse.core.Parsed
import interactive.Tokens.Payload
import interactive.parser.{JsonParser, Parser}
import org.scalatest.FlatSpec
import org.scalatest.prop.TableDrivenPropertyChecks

/**
  * Created by bspriggs on 11/13/2016.
  */
trait InteractiveModeParserFixtures extends FlatSpec with TableDrivenPropertyChecks {

  val parser = Parser

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

    def optionJson(s: String = validJson): Option[Payload] = JsonParser.jsonExpr.parse(s) match {
      case o: Parsed.Success[_,_,_] => o match {
        case Parsed.Success(opt:Payload, _) => Some(opt)
        case _ => None
      }
      case _ => None
    }

    def parsedJson(s: String = validJson): Payload = {
      optionJson(s) match {
        case Some(parsed) => parsed
        case None => parsedJson("{}")
      }
    }

    val literal_sql = """CREATE TABLE bobby (name VARCHAR(40))"""
  }
  val f = fixture

  def doesParseToA[P](a: String,
                      parsed: P,
                      pr: all.Parser[Product with Serializable]
                      = parser.expression ) = {
    try {
      val Parsed.Success(par, _) = pr.parse(a)
      assertResult(parsed.getClass, "Expected: " + parsed.getClass.toString)(par.getClass)
    } catch {
      case me: MatchError => fail(s"While parsing `$a`, encountered error:" + me.getMessage())// The parsing failed
    }
  }

  def doesNotParseToA[P](a: String,
                         parsed: P,
                         pr: all.Parser[Product with Serializable]
                         = parser.expression ) = {
    try {
      val Parsed.Success(par, _) = pr.parse(a)
      assert(par.getClass != parsed.getClass,
        par.getClass.toString + " should not parse to " + parsed.getClass.toString)
    } catch {
      case _: MatchError => succeed
    }
  }

}
