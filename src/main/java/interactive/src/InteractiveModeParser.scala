
/**
  * Created by bspriggs on 11/12/2016.
  */
import fastparse.all._

object InteractiveModeParser {

  // InteractiveMode representation/ AST Tokens
  object InteractiveMode {
    sealed trait Statement extends Any {
      def value: Any
      //def apply(s: java.lang.String): Statement =
      //  this.asInstanceOf[Obj].value.find(_._1 == s).get._2
    }

    case class Help(value: java.lang.String) extends AnyVal with Statement
    case class Obj(value: (Type.One, Payload)*) extends AnyVal with Statement
    case class SuperObj(value: (Type.Many, Option[Payload])) extends AnyVal with Statement
    case class Request(value: java.lang.String) extends AnyVal with Statement
    case class SQL(value: java.lang.String) extends AnyVal with Statement
    type Payload = JsonParser.Js.Val

    object Type {
        case class One(value: java.lang.String) extends AnyVal with Statement
        case class Many(value: java.lang.String) extends AnyVal with Statement
    }

    case object Stop extends Statement {
      def value = Stop
    }
  }

  val whitespace = P( CharsWhile(" \r\n\t".contains(_: Char)).? ).opaque("")

  val _stop = P( ("quit" | "bye").? ~ End)
    .map(_ => InteractiveMode.Stop)
  val _help = P( ("help" | "?") ~ whitespace ~ AnyChar.rep.! ~ End )
    .map(InteractiveMode.Help)
  val _request = P( ("create" | "show" | "update" | "delete" | "write").! )
    .map(InteractiveMode.Request)

  private def obj(tuples: (InteractiveMode.Type.One, InteractiveMode.Payload)*) = {
    InteractiveMode.Obj(tuples:_*)
  }
  private def superobj(tup: (InteractiveMode.Type.Many, Any)) = {
    val (t, opt) = tup
    InteractiveMode.SuperObj(t, opt.asInstanceOf[Option[JsonParser.Js.Obj]])
  }


  val `type` = P( ("user" | "member" | "provider" | "service" ).! )
  val _singleType = P( `type` ~/ !"s" ).map(InteractiveMode.Type.One)
  val _manyType = P( `type` ~/ "s" ).map(InteractiveMode.Type.Many)
  val _payload = JsonParser.jsonExpr // courtesy of Li Haoyi

  val `_object` = P( (_singleType ~/ whitespace ~/ _payload).rep(1) )
    .map(obj(_:_*))
  val _superobject =
    P( "all" ~/ whitespace ~/ _manyType ~/ (whitespace ~ _payload ).? ~/ End
    | _manyType ~/ whitespace ~/ _payload ).map(superobj)
  val _sql_literal =
    P( "SQL"
      ~ whitespace
      ~ AnyChar.rep(1).!
      ~ End).map(InteractiveMode.SQL)

  val stop        = _stop.opaque("<stop>")
  val help        = _help.opaque("<help>")
  val request     = _request.opaque("<request>")
  val `object`    = `_object`.opaque("<object>")
  val superobject = _superobject.opaque("<objects>")
  val sql_literal = _sql_literal.opaque("<SQL query>")

  val expr = P( stop
    | help
    | ( request ~/ whitespace
        ~/ (`object`.rep(1) | superobject ))
      .opaque("<request> with <object> or <objects>")
    | sql_literal )
}
