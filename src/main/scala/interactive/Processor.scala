package interactive

import interactive.Term._
import interactive.action.{SqlAction, _}
import interactive.parser.JsonParser.Js
import sqldb.dbo.DatabaseObject.DatabaseAction

import scala.collection.mutable.ArrayBuffer

/**
  * Created by bspriggs on 11/30/2016.
  */
class Processor {
  // Process a sequence of statements to SQL or File Actions
  def process(statements: ArrayBuffer[_]) = Unit

  // handle a discrete object request
  // don't worry about object types
  private def handleMonoRequest[R](request: Request, objs: Obj):
    Option[Action] = request match {
    case r: Request => Some(SqlAction[R])
    case _ => None
  }

  // handle a superobject request
  private def handlePolyRequest[R](request: Request, superobj: SuperObj):
    Option[Action] = request match {
    case _ => None
  }

  // Process a  statements to a SQL or File Action
  def process[R](statement: Statement): Option[Action] =
    statement match {
      case Stop => None
      case Help(string) => None // TODO: Add 'display help' action
      case SQL(query) => None // TODO: Add generic execute SQL action
      case Mono((request, objs)) => handleMonoRequest[R](request, objs)
      case Poly((request, superobj)) => handlePolyRequest[R](request, superobj)
    }

  // this is a rather big assumption
  def actionEither(request: Request): Either[DatabaseAction, WriteFileAction] = databaseActionMatch(request) match {
    case Some(v) => Left(v)
    case None => fileActionMatch(request) match {
      case Some(v) => Right(v)
    }
  }

  def isValidAction(request: Request): Boolean = databaseActionMatch(request) match {
    case Some(v) => true
    case None => fileActionMatch(request) match {
      case Some(_) => true
    }
    case _ => false
  }

  def loadClass(classname: String): Class[_] = {
    Class.forName("sqldb.dbo." + classname)
  }

  def hasField(field: String, obj: Js.Val): Boolean = obj.value match {
    case n: ArrayBuffer[(String, Js.Val)] =>
      n.exists { tup: (String, Js.Val) => tup._1 == field }
    case _ => false
  }

  def hasFields(fields: List[String], array: ArrayBuffer[(String, Js.Val)]): Boolean = {
    fields.forall(s => array.exists(t => s == t._1))
  }

  def databaseActionMatch(request: Request): Option[DatabaseAction] = request match {
    case Request(s) => s match {
      case "create" => Some(DatabaseAction.CREATE)
      case "show" => Some(DatabaseAction.SHOW)
      case "update" => Some(DatabaseAction.SHOW)
      case "destroy" => Some(DatabaseAction.DELETE)
      case _ => None
    }
  }

  def fileActionMatch(request: Request): Option[WriteFileAction] = request match {
    case Request(s) => s match {
      case "write" => Some(WriteFileAction)
      case _ => None
    }
  }


  def isValidDatabaseAction(request: Request, obj: Obj): Boolean = databaseActionMatch(request) match {
    case Some(action)
      if hasFields(listFor(action, Obj),
        obj.value.asInstanceOf[ArrayBuffer[(String, Js.Val)]]) => true
    case None => false
  }

  // TODO: implement
  def isValidFileAction(request: Request, obj: Js.Val): Boolean = true

  def listFor(s: String): Option[List[String]] = s match {
    case "user" => Some(List("username"))
    case "member" => Some(List("number"))
    case "provider" => Some(List("number"))
    case "service" => Some(List("service_code", "service_id")) // can contain either
    case _ => None
  }

  def listFor(one: One): Option[List[String]] = one match {
    case One(s) => listFor(s)
    case _ => None
  }

  // give the list of fields needed for a particular plural type
  def listFor(many: interactive.Term.Many): Option[List[String]] = many match {
    case Many(s) => listFor(s)
    case _ => None
  }
  // generate a list of required fields in a json object for a particular type
  def listFor(action: DatabaseAction, obj: Obj): List[String] = action match {
    case DatabaseAction.CREATE =>
  }
}
