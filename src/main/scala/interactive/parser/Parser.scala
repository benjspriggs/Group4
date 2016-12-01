package interactive.parser


/**
  * Created by bspriggs on 11/12/2016.
  */
import fastparse.all._
import interactive.Statements.{Mono, Poly, SQL}
import interactive.Term._
import interactive.{Statements, Term}

object Parser {
  lazy val endingSemicolon = P( ";" ~ whitespace.? ~ End)

  lazy val whitespace = P( CharsWhile(" \r\n\t".contains(_: Char)) ).opaque("")

  lazy val _stop = P(  "quit" | "bye" | "exit" )
    .map(_ => Statements.Stop)
  lazy val _help = P( ("help" | "?") ~/ (whitespace ~ AnyChar.rep.!).? )
    .map(Statements.Help)
  lazy val _request = P( ("create" | "show" | "update" | "delete" | "write").! )
    .map(Term.Request)

  lazy val `type`      = P( ("user" | "member" | "provider" | "service" ).! )
  lazy val _singleType = P( `type` ~/ !"s" ~ (whitespace ~ "report".!).? ).map({
    case (t, Some(r)) => Term.Type.One(s"$t $r")
    case (t, None) => Term.Type.One(t)})

  lazy val _manyType   = P( `type` ~ "s" ~ (whitespace ~ "reports".!).? ).map({
    case (t, Some(r)) => Term.Type.Many(s"$t $r")
    case (t, None) => Term.Type.Many(t)})

  lazy val _payload = JsonParser.jsonExpr // courtesy of Li Haoyi

  lazy val `_object` =
    P( _singleType ~/ whitespace ~/ _payload.rep(1) ).map(Term.Obj)
  lazy val _superobject =
    P( "all"
      ~/ whitespace
      ~ _manyType
      ~/ (whitespace ~ _payload ).?
      | _manyType ~/ whitespace ~ _payload.? ).map(Term.SuperObj)

  lazy val _request_object = P( _request ~/ whitespace ~/ ( _superobject | `object` ))
    .map(statementsMap)

  def statementsMap(parsed_tuple: (Request, Equals)) =
  {
    parsed_tuple._2 match {
      case t: Equals => t match {
        case t: SuperObj => Poly(parsed_tuple._1, t)
        case t: Obj => Mono(parsed_tuple._1, t)
      }
    }
  }


  lazy val _sql_literal =
    P( "SQL"
      ~/ whitespace
      ~/ AnyChar.rep(1).! )
      .map(SQL)

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

  lazy val _non_terminating_statement = P( ( _help | _request_object | _sql_literal ) ~ whitespace.? ~ ";" )
  lazy val non_terminating_statement =
    P( ( _help | _request_object | _sql_literal ) ~ whitespace.? ~ ";" )
      .opaque("<non-terminating statement>")
  lazy val _statements = P( ( _non_terminating_statement ~ whitespace.?).rep(0) ~_stop ~ endingSemicolon )
    .map(_._1)
  lazy val statements = P( ( non_terminating_statement ~ whitespace.?).rep(0) ~stop ~ endingSemicolon )
}
