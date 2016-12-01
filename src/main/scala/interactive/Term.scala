package interactive

import interactive.parser.JsonParser

/**
  * Created by bspriggs on 11/13/2016.
  *
  * Interactive Mode AST Tokens
  */
object Term {
  /**
    * A JSON object with a clarifying type.
    * e.g. user { "name": "Barack Obama" }
    * @param value A list of type and Payload tuple pairs
    */
  case class Obj(value: (Type.One, Seq[Payload])) extends AnyVal

  /**
    * A JSON object with a pluralizing type.
    * e.g. users { "status": "valid" }
    * @param value A plural type and Payload pair
    */
  case class SuperObj(value: (Type.Many, Option[Payload])) extends AnyVal

  /**
    * CRUD operations, and write-to-disk operations.
    * @param value The requested operation
    */
  case class Request(value: java.lang.String) extends AnyVal

  type Payload = JsonParser.Js.Val

  object Type {
    /**
      * A single user, member, etc.
      * @param value Object type
      */
    case class One(value: java.lang.String) extends AnyVal

    /**
      * A plural user, member, etc.
      * @param value Object type
      */
    case class Many(value: java.lang.String) extends AnyVal
  }

}
