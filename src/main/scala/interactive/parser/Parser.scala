package interactive.parser


/**
  * Created by bspriggs on 11/12/2016.
  */
import fastparse.all._
import interactive.Tokens

object Parser {
  lazy val semiEnd = P( ";" ~ End )

  lazy val whitespace = P( CharsWhile(" \r\n\t".contains(_: Char)) ).opaque("<whitespace>")

  lazy val _stop = P( ( "quit" | "bye" | "exit") ~/ semiEnd | semiEnd  )
    .map(_ => Tokens.Stop)
  lazy val _help = P( ("help" | "?") ~/ (whitespace ~ CharsWhile(_ != ';').!) .? ~/ semiEnd )
    .map(Tokens.Help)
  lazy val _request = P( ("create" | "show" | "update" | "delete" | "write").! )
    .map(Tokens.Request)

  lazy val `type`      = P( ("user" | "member" | "provider" | "service" ).! )
  lazy val _singleType = P( `type` ~/ !"s" ~ (whitespace ~ "report".!).? ).map({
    case (t, Some(r)) => Tokens.Type.One(s"$t $r")
    case (t, None) => Tokens.Type.One(t)})

  lazy val _manyType   = P( `type` ~ "s" ~ (whitespace ~ "reports".!).? ).map({
    case (t, Some(r)) => Tokens.Type.Many(s"$t $r")
    case (t, None) => Tokens.Type.Many(t)})

  lazy val _payload = JsonParser.jsonExpr // courtesy of Li Haoyi

  lazy val `_object` =
    P( (_singleType ~/ whitespace ~/ _payload).rep(1) )
    .map(Tokens.Obj(_:_*))
  lazy val _superobject =
    P( "all"
      ~/ whitespace
      ~ _manyType
      ~/ (whitespace ~ _payload ).? ~/ semiEnd
      | _manyType ~/ whitespace ~ _payload.? ~/ semiEnd ).map(Tokens.SuperObj)

  lazy val _request_object = P( _request ~/ whitespace ~/ ( _superobject | `object`.rep(1)))

  lazy val _sql_literal =
    P( "SQL"
      ~/ whitespace
      ~/ AnyChar.rep(1).!
      ~/ semiEnd)
      .map(Tokens.SQL)

  lazy val stop           = _stop          .opaque("<stop>")
  lazy val help           = _help          .opaque("<help>")
  lazy val request        = _request       .opaque("<request>")
  lazy val `object`       = `_object`      .opaque("<object>")
  lazy val superobject    = _superobject   .opaque("<objects>")
  lazy val sql_literal    = _sql_literal   .opaque("<SQL query>")
  lazy val request_object = _request_object.opaque("<request> with <object> or <objects>")

  lazy val expression = P( stop
    | help
    | request_object
    | sql_literal )

  lazy val _expression = P( _stop
      | _help
      | _request_object
      | _sql_literal )
}
