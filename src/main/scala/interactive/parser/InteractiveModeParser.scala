package interactive.parser


/**
  * Created by bspriggs on 11/12/2016.
  */
import fastparse.all._
import interactive.token.InteractiveMode

object InteractiveModeParser {

  val whitespace = P( CharsWhile(" \r\n\t".contains(_: Char)) ).opaque("")

  val _stop = P( ("quit" | "bye" | "exit" ).? ~ End)
    .map(_ => InteractiveMode.Stop)
  val _help = P( ("help" | "?") ~ (whitespace ~ AnyChar.rep.!).? ~ End )
    .map(InteractiveMode.Help)
  val _request = P( ("create" | "show" | "update" | "delete" | "write").! )
    .map(InteractiveMode.Request)

  val `type`      = P( ("user" | "member" | "provider" | "service" ).! )
  val _singleType = P( `type` ~ !"s" ).map(InteractiveMode.Type.One)
  val _manyType   = P( `type` ~ "s" ) .map(InteractiveMode.Type.Many)

  val _payload = JsonParser.jsonExpr // courtesy of Li Haoyi

  val `_object` =
    P( (_singleType ~ whitespace ~ _payload).rep(1) )
    .map(InteractiveMode.Obj(_:_*))
  val _superobject =
    P( "all"
      ~ whitespace
      ~ _manyType
      ~ (whitespace ~ _payload ).? ~ End
      | _manyType ~ whitespace ~ _payload.? ).map(InteractiveMode.SuperObj)

  val _request_object = P( _request ~ whitespace ~ ( _superobject | `object`.rep(1)))

  val _sql_literal =
    P( "SQL"
      ~ whitespace
      ~ AnyChar.rep(1).!
      ~ End)
      .map(InteractiveMode.SQL)

  val stop           = _stop          .opaque("<stop>")
  val help           = _help          .opaque("<help>")
  val request        = _request       .opaque("<request>")
  val `object`       = `_object`      .opaque("<object>")
  val superobject    = _superobject   .opaque("<objects>")
  val sql_literal    = _sql_literal   .opaque("<SQL query>")
  val request_object = _request_object.opaque("<request> with <object> or <objects>")

  val expr = P( stop
    | help
    | request_object
    | sql_literal )

  val _expr = P( _stop
      | _help
      | _request_object
      | _sql_literal )
}
