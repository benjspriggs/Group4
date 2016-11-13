/**
  * Created by kn on 11/12/2016.
  */


object InteractiveModeParser {
  import fastparse.all._

  // InteractiveMode representation/ AST Tokens
  object InteractiveMode {
    sealed trait Statement extends Any {
      def value: Any
      def apply(s: java.lang.String): Statement =
        this.asInstanceOf[Obj].value.find(_._1 == s).get._2
    }

    case class Help(value: java.lang.String) extends AnyVal with Statement
    case class Obj(value: (java.lang.String, Statement)*) extends AnyVal with Statement
    case class SuperObj(value: (Type.Many, Option[Payload])) extends AnyVal with Statement
    case class Request(value: java.lang.String) extends AnyVal with Statement
    case class SQL(value: java.lang.String) extends AnyVal with Statement
    case class Payload(value: JsonParser.Js.Obj) extends AnyVal with Statement

    object Type {
        case class One(value: java.lang.String) extends AnyVal with Statement
        case class Many(value: java.lang.String) extends AnyVal with Statement
    }

    case object Stop extends Statement {
      def value = Stop
    }
  }

  val whitespace = P( CharsWhile(" \r\n\t".contains(_: Char)).? )

  val stop = P( ("quit" | "bye") ~ End)
    .map(_ => InteractiveMode.Stop)
  val help = P( ("help" | "?") ~ whitespace ~ AnyChar.rep.! ~ End )
    .map(InteractiveMode.Help)
  val request = P( ("create" | "show" | "update" | "delete" | "write").! )
    .map(InteractiveMode.Request)

  private def plural(tuple: (java.lang.String, Option[String]))= {
    val (t, s) = tuple
    s match {
      case Some(_) => InteractiveMode.Type.Many(t)
      case None => InteractiveMode.Type.One(t)
    }
  }

  private def superobj(tup: (InteractiveMode.Type.Many, Object)) = {
    val (t, opt) = tup
    (t, opt.asInstanceOf[Option[JsonParser.Js.Obj]])
  }

  val `type` = P( ("user" | "member" | "provider" | "service" ).! )
  val singleType = P( `type` ~ !"s" ).map(InteractiveMode.Type.One)
  val manyType = P( `type` ~ "s" ).map(InteractiveMode.Type.Many)
  val payload = JsonParser.jsonExpr // courtesy of Li Haoyi
  val `object` = P( singleType ~ whitespace ~ payload ).map(InteractiveMode.Obj(_:_*))
  val superobject =
    P( "all" ~ whitespace ~ manyType ~ (whitespace ~ payload ).?
    | manyType ~ whitespace ~ payload ).map(superobj)
  val sql_literal =
    P( "SQL"
      ~ whitespace
      ~ AnyChar.rep(1).!
      ~ End).map(InteractiveMode.SQL)

  val expr = P( stop
    | help
    | request ~ whitespace
        ~ (`object`.rep(1) | superobject)
    | sql_literal )

  // JSON parser and AST builder, courtesy of:
  // http://www.lihaoyi.com/fastparse/
  object JsonParser {
    object Js {
      sealed trait Val extends Any {
        def value: Any
        def apply(i: Int): Val = this.asInstanceOf[Arr].value(i)
        def apply(s: java.lang.String): Val =
          this.asInstanceOf[Obj].value.find(_._1 == s).get._2
      }

      case class Str(value: java.lang.String) extends AnyVal with Val
      case class Obj(value: (java.lang.String, Val)*) extends AnyVal with Val
      case class Arr(value: Val*) extends AnyVal with Val
      case class Num(value: Double) extends AnyVal with Val
      case object False extends Val{
        def value = false
      }
      case object True extends Val{
        def value = true
      }
      case object Null extends Val{
        def value = null
      }
    }

    case class NamedFunction[T, V](f: T => V, name: String) extends (T => V){
      def apply(t: T) = f(t)
      override def toString() = name

    }

    val Whitespace = NamedFunction(" \r\n".contains(_: Char), "Whitespace")
    val Digits = NamedFunction('0' to '9' contains (_: Char), "Digits")
    val StringChars = NamedFunction(!"\"\\".contains(_: Char), "StringChars")

    val space         = P( CharsWhile(Whitespace).? )
    val digits        = P( CharsWhile(Digits))
    val exponent      = P( CharIn("eE") ~ CharIn("+-").? ~ digits )
    val fractional    = P( "." ~ digits )
    val integral      = P( "0" | CharIn('1' to '9') ~ digits.? )

    val number = P( CharIn("+-").? ~ integral ~ fractional.? ~ exponent.? ).!.map(
      x => Js.Num(x.toDouble)
    )

    val `null`        = P( "null" ).map(_ => Js.Null)
    val `false`       = P( "false" ).map(_ => Js.False)
    val `true`        = P( "true" ).map(_ => Js.True)

    val hexDigit      = P( CharIn('0'to'9', 'a'to'f', 'A'to'F') )
    val unicodeEscape = P( "u" ~ hexDigit ~ hexDigit ~ hexDigit ~ hexDigit )
    val escape        = P( "\\" ~ (CharIn("\"/\\bfnrt") | unicodeEscape) )

    val strChars = P( CharsWhile(StringChars) )
    val string =
      P( space ~ "\"" ~/ (strChars | escape).rep.! ~ "\"").map(Js.Str)

    val array =
      P( "[" ~/ jsonExpr.rep(sep=",".~/) ~ space ~ "]").map(Js.Arr(_:_*))

    val pair = P( string.map(_.value) ~/ ":" ~/ jsonExpr )

    val obj =
      P( "{" ~/ pair.rep(sep=",".~/) ~ space ~ "}").map(Js.Obj(_:_*))

    val jsonExpr: P[Js.Val] = P(
      space ~ (obj | array | string | `true` | `false` | `null` | number) ~ space
    )
  }
}
