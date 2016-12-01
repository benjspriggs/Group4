package interactive.parser


/**
  * Created by bspriggs on 11/12/2016.
  */
import fastparse.all._
import interactive.Statements.{Mono, Poly, SQL}
import interactive.Term._
import interactive.{Statements, Term}

object Parser {
  lazy val delim: Char = ';'
  lazy val finalDelim = P( delim.toString ~ whitespace.? ~ End)

  lazy val whitespace = P( CharsWhile(" \r\n\t".contains(_: Char)) ).opaque("")

  lazy val stop = P(  "quit" | "bye" | "exit" )
    .map(_ => Statements.Stop).opaque("expected token <stop>")
  lazy val help = P( ("help" | "?") ~/ (whitespace ~ CharsWhile(_ != delim).!).? )
    .map(Statements.Help).opaque("expected token <help>")
  lazy val request = P( ("create" | "show" | "update" | "delete" | "write").! )
    .map(Term.Request).opaque("expected token <request>")

  lazy val `type`      = P( ("user" | "member" | "provider" | "service" ).! )
    .opaque("expected token <type>")
  lazy val _singleType = P( `type` ~/ !"s" ~ (whitespace ~ "report".!).? ).map({
    case (t, Some(r)) => Term.Type.One(s"$t $r")
    case (t, None) => Term.Type.One(t)})

  lazy val _manyType   = P( `type` ~ "s" ~ (whitespace ~ "reports".!).? ).map({
    case (t, Some(r)) => Term.Type.Many(s"$t $r")
    case (t, None) => Term.Type.Many(t)})

  lazy val _payload = JsonParser.jsonExpr // courtesy of Li Haoyi

  lazy val `object` =
    P( _singleType ~/ whitespace ~/ _payload.rep(1) ).map(Term.Obj)
  lazy val superobject =
    P( "all"
      ~/ whitespace
      ~ _manyType
      ~/ (whitespace ~ _payload ).?
      | _manyType ~/ whitespace ~ _payload.? ).map(Term.SuperObj)

  lazy val request_object = P( request ~/ whitespace ~/ ( superobject | `object` ))
    .map(statementsMap).opaque("<request> with <object> or <objects>")

  def statementsMap(parsed_tuple: (Request, Equals)) =
  {
    parsed_tuple._2 match {
      case t: Equals => t match {
        case t: SuperObj => Poly(parsed_tuple._1, t)
        case t: Obj => Mono(parsed_tuple._1, t)
      }
    }
  }

  lazy val sql_literal =
    P( "SQL"
      ~/ whitespace
      ~/ AnyChar.rep(1).! )
      .map(SQL).opaque("<SQL query>")


  lazy val expression = P( stop
    | help
    | request_object
    | sql_literal )

  lazy val non_terminating_statement =
    P( ( help | request_object | sql_literal ) ~ whitespace.? ~ delim.toString )
      .opaque("<non-terminating statement>")
  lazy val statements = P( ( non_terminating_statement ~/ whitespace.?).rep(0) ~ stop ~/ finalDelim )
    .map(_._1)
}
