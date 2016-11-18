package interactive.parser

/**
  * Created by bspriggs on 11/13/2016.
  *
  * Parser courtesy of Li Haoyi:
  * http://www.lihaoyi.com/fastparse/#Json
  */

import fastparse.all._
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

  lazy val space         = P( CharsWhile(Whitespace).? )
  lazy val digits        = P( CharsWhile(Digits))
  lazy val exponent      = P( CharIn("eE") ~ CharIn("+-").? ~ digits )
  lazy val fractional    = P( "." ~ digits )
  lazy val integral      = P( "0" | CharIn('1' to '9') ~ digits.? )

  lazy val number = P( CharIn("+-").? ~ integral ~ fractional.? ~ exponent.? ).!.map(
    x => Js.Num(x.toDouble)
  )

  lazy val `null`        = P( "null" ).map(_ => Js.Null)
  lazy val `false`       = P( "false" ).map(_ => Js.False)
  lazy val `true`        = P( "true" ).map(_ => Js.True)

  lazy val hexDigit      = P( CharIn('0'to'9', 'a'to'f', 'A'to'F') )
  lazy val unicodeEscape = P( "u" ~ hexDigit ~ hexDigit ~ hexDigit ~ hexDigit )
  lazy val escape        = P( "\\" ~ (CharIn("\"/\\bfnrt") | unicodeEscape) )

  lazy val strChars = P( CharsWhile(StringChars) )
  lazy val string =
    P( space ~ "\"" ~/ (strChars | escape).rep.! ~ "\"").map(Js.Str)

  lazy val array =
    P( "[" ~/ jsonExpr.rep(sep=",".~/) ~ space ~ "]").map(Js.Arr(_:_*))

  lazy val pair = P( string.map(_.value) ~/ ":" ~/ jsonExpr )

  lazy val obj =
    P( "{" ~/ pair.rep(sep=",".~/) ~ space ~ "}").map(Js.Obj(_:_*))

  lazy val jsonExpr: P[Js.Val] = P(
    space ~ (obj | array | string | `true` | `false` | `null` | number) ~ space
  )
}
