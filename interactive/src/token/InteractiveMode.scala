package token

import parser.JsonParser

/**
  * Created by bspriggs on 11/13/2016.
  *
  * Interactive Mode AST Tokens
  */
object InteractiveMode {
  sealed trait Statement extends Any {
    def value: Any
  }

  /**
    * Allows a user to gain more information.
    * about a reserved word or the syntax of the Interactive Mode.
    * @param value Whatever the user wants to know, from the reserved words
    *              or keywords
    */
  case class Help(value: Option[java.lang.String]) extends AnyVal with Statement

  /**
    * A JSON object with a clarifying type.
    * e.g. user { "name": "Barack Obama" }
    * @param value A list of type and Payload tuple pairs
    */
  case class Obj(value: (Type.One, Payload)*) extends AnyVal with Statement

  /**
    * A JSON object with a pluralizing type.
    * e.g. users { "status": "valid" }
    * @param value A plural type and Payload pair
    */
  case class SuperObj(value: (Type.Many, Option[Payload])) extends AnyVal with Statement

  /**
    * CRUD operations, and write-to-disk operations.
    * @param value The requested operation
    */
  case class Request(value: java.lang.String) extends AnyVal with Statement

  /**
    * Literal SQL, when the grammar just isn't cutting it.
    * @param value The literal SQL query, baretext or quoted
    */
  case class SQL(value: java.lang.String) extends AnyVal with Statement

  type Payload = JsonParser.Js.Val

  object Type {

    /**
      * A single user, member, etc.
      * @param value Object type
      */
    case class One(value: java.lang.String) extends AnyVal with Statement

    /**
      * A plural user, member, etc.
      * @param value Object type
      */
    case class Many(value: java.lang.String) extends AnyVal with Statement
  }

  /**
    * A request to stop a session.
    */
  case object Stop extends Statement {
    def value = Stop
  }
}

