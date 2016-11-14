/**
  * Created by bspriggs on 11/13/2016.
  *
  * Interactive Mode AST Tokens
  */
object InteractiveMode {
  sealed trait Statement extends Any {
    def value: Any
  }

  case class Help(value: Option[java.lang.String]) extends AnyVal with Statement
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


