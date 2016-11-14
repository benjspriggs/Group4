
/**
  * Created by bspriggs on 11/12/2016.
  */
import fastparse.all._

object InteractiveModeParser {

  val whitespace = P( CharsWhile(" \r\n\t".contains(_: Char)) ).opaque("")

  val _stop = P( ("quit" | "bye" | "exit" ).? ~ End)
    .map(_ => InteractiveMode.Stop)
  val _help = P( ("help" | "?") ~ (whitespace ~ AnyChar.rep.!).? ~ End )
    .map(InteractiveMode.Help)
  val _request = P( ("create" | "show" | "update" | "delete" | "write").! )
    .map(InteractiveMode.Request)

  private def obj(tuples: (InteractiveMode.Type.One, InteractiveMode.Payload)*) = {
    InteractiveMode.Obj(tuples:_*)
  }
  private def superobj(tup: (InteractiveMode.Type.Many, Any)) = {
    val (t, opt) = tup
    opt match { // TODO: this is an ugly, fix with some structural changes of how superobjects are paresd
      case opt_cast: JsonParser.Js.Obj =>
        InteractiveMode.SuperObj((t, Some(opt_cast)))
      case o: Option[JsonParser.Js.Obj] =>
        InteractiveMode.SuperObj(t, o)
    }
  }


  val `type` = P( ("user" | "member" | "provider" | "service" ).! )
  val _singleType = P( `type` ~ !"s" ).map(InteractiveMode.Type.One)
  val _manyType = P( `type` ~ "s" ).map(InteractiveMode.Type.Many)
  val _payload = JsonParser.jsonExpr // courtesy of Li Haoyi

  val `_object` = P( (_singleType ~ whitespace ~ _payload).rep(1) )
    .map(obj(_:_*))
  val _superobject =
    P( "all" ~ whitespace ~ _manyType ~ (whitespace ~ _payload ).? ~ End
    | _manyType ~ whitespace ~ _payload ).map(superobj)
  val _sql_literal =
    P( "SQL"
      ~ whitespace
      ~ AnyChar.rep(1).!
      ~ End).map(InteractiveMode.SQL)

  val stop        = _stop       // .opaque("<stop>")
  val help        = _help       // .opaque("<help>")
  val request     = _request    // .opaque("<request>")
  val `object`    = `_object`   // .opaque("<object>")
  val superobject = _superobject// .opaque("<objects>")
  val sql_literal = _sql_literal// .opaque("<SQL query>")

  val expr = P( stop
    | help
    | ( request ~ whitespace
        ~ ( superobject | `object`.rep(1) ))
      // .opaque("<request> with <object> or <objects>")
    | sql_literal )
}
