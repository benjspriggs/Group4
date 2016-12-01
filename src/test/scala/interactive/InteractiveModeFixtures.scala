package interactive

import com.mysql.cj.x.json.JsonParser
import fastparse.core.Parsed
import interactive.Term.Payload
import org.scalatest.FlatSpec
import org.scalatest.prop.TableDrivenPropertyChecks
;

/**
  * Created by bspriggs on 11/30/2016.
  */
trait InteractiveModeFixtures extends FlatSpec with TableDrivenPropertyChecks{
        def fixture=new{
                val helpRequests=Table("word",
                        "help",
                        "?"
                )

                val requests=Table(
                        "name",
                        "create",
                        "show",
                        "update",
                        "delete",
                        "write"
                )

                val typeSingle=Table(
                        "type",
                        "user",
                        "member",
                        "provider",
                        "service"
                )

                val typeMany=Table(
                        "types",
                        "users",
                        "members",
                        "providers",
                        "services"
                )

                val stopRequests=Table("word",
                        "quit",
                        "bye",
                        "exit",
                        ""
                )

                val validJson="""
                                |{
                                |"name":"Kate Abernathy",
                                |"age":23,
                                |"member_number":4
                                |}
                              """.stripMargin

                def optionJson(s:String=validJson):Option[Payload]=JsonParser.jsonExpr.parse(s)match{
                        case o:Parsed.Success[_,_,_]=>o match{
                                case Parsed.Success(opt:Payload,_)=>Some(opt)
                                case _=>None
                        }
                        case _=>None
                }

                def parsedJson(s:String=validJson):Payload={
                        optionJson(s)match{
                                case Some(parsed)=>parsed
                                case None=>parsedJson("{}")
                        }
                }

                val literal_sql="""CREATE TABLE bobby (name VARCHAR(40))"""
        }
        val f=fixture
}
