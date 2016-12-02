package interactive

import interactive.Term.{Obj, Request, SuperObj}

/**
  * Created by bspriggs on 11/30/2016.
  */
abstract class Statement
/**
  * Allows a user to gain more information.
  * about a reserved word or the syntax of the Interactive Mode.
  * @param value Whatever the user wants to know, from the reserved words
  *              or keywords
  */
case class Help(value: Option[String]) extends Statement

/**
  * A request that applies to potentially
  * multiple objects.
  * @param value A tuple with the request and modified general object
  */
case class Poly(value: (Request, SuperObj)) extends Statement

/**
  * A request that applies to discrete objects,
  * that are each unique.
  * @param value A sequence of tuples with the request and object(s)
  */
case class Mono(value: (Request, Obj)) extends Statement

/**
  * Literal SQL, when the grammar just isn't cutting it.
  * @param value The literal SQL query, baretext or quoted
  */
case class SQL(value: String) extends Statement

/**
  * A request to stop a session.
  */
case object Stop extends Statement

