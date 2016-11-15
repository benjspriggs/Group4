package fixtures

import fastparse.all
import fastparse.core.Parsed
import org.scalatest.FlatSpec
import org.scalatest.prop.TableDrivenPropertyChecks
import parser.InteractiveModeParser

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


  def doesParseToA[P](a: String,
                      parsed: P,
                      pr: all.Parser[Product with Serializable]
                      = parser.expr ) = {
    assert(pr.parse(a) match {
      case Parsed.Success(parsed, _) => true
      case _ => false
    })
  }

  def doesNotParseToA[P](a: String,
                         parsed: P,
                         pr: all.Parser[Product with Serializable]
                         = parser.expr ) = {
    assert(pr.parse(a) match {
      case Parsed.Failure(parsed, _, _ ) => true
      case _ => false
    })
  }

}
