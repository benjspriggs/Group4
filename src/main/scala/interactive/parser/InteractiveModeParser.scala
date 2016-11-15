package interactive.parser


/**
  * Created by bspriggs on 11/12/2016.
  */
import fastparse.all._
import interactive.token.InteractiveMode

object InteractiveModeParser {

  lazy val whitespace = P( CharsWhile(" \r\n\t".contains(_: Char)) ).opaque("")

  lazy val _stop = P( ("quit" | "bye" | "exit" ).? ~ End)
    .map(_ => InteractiveMode.Stop)
  lazy val _help = P( ("help" | "?") ~ (whitespace ~ AnyChar.rep.!).? ~ End )
    .map(InteractiveMode.Help)
  lazy val _request = P( ("create" | "show" | "update" | "delete" | "write").! )
    .map(InteractiveMode.Request)

  lazy val `type`      = P( ("user" | "member" | "provider" | "service" ).! )
  lazy val _singleType = P( `type` ~ !"s" ).map(InteractiveMode.Type.One)
  lazy val _manyType   = P( `type` ~ "s" ) .map(InteractiveMode.Type.Many)

  lazy val _payload = JsonParser.jsonExpr // courtesy of Li Haoyi

  lazy val `_object` =
    P( (_singleType ~ whitespace ~ _payload).rep(1) )
    .map(InteractiveMode.Obj(_:_*))
  lazy val _superobject =
    P( "all"
      ~ whitespace
      ~ _manyType
      ~ (whitespace ~ _payload ).? ~ End
      | _manyType ~ whitespace ~ _payload.? ).map(InteractiveMode.SuperObj)

  lazy val _request_object = P( _request ~ whitespace ~ ( _superobject | `object`.rep(1)))

  lazy val _sql_literal =
    P( "SQL"
      ~ whitespace
      ~ AnyChar.rep(1).!
      ~ End)
      .map(InteractiveMode.SQL)

  lazy val stop           = _stop          .opaque("<stop>")
  lazy val help           = _help          .opaque("<help>")
  lazy val request        = _request       .opaque("<request>")
  lazy val `object`       = `_object`      .opaque("<object>")
  lazy val superobject    = _superobject   .opaque("<objects>")
  lazy val sql_literal    = _sql_literal   .opaque("<SQL query>")
  lazy val request_object = _request_object.opaque("<request> with <object> or <objects>")

  lazy val expr = P( stop
    | help
    | request_object
    | sql_literal )

  lazy val _expr = P( _stop
      | _help
      | _request_object
      | _sql_literal )
}
