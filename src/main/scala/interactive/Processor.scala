package interactive

import interactive.Term.{Obj, Request, SuperObj}
import interactive.action._
import interactive.parser.JsonParser.Js
import sqldb.dbo.DatabaseObject.DatabaseAction

import scala.collection.mutable.ArrayBuffer

/**
  * Created by bspriggs on 11/30/2016.
  */
class Processor {
  // Process a sequence of statements to SQL or File Actions
  def process(statements: ArrayBuffer[_]) = Unit

  private def handleMonoRequest[R](request: Request, objs: Obj):
    Option[ReturnableAction[R]] = request match {
    case Request(s) => s match {
      case "create" => None
      case "show" => None
      case "update" => None
      case "destroy" => None
      case "write" => None
    }
    case _ => None
  }

  private def handlePolyRequest[R](request: Request, superobj: SuperObj):
    Option[ReturnableAction[R]] = request match {
    case _ => None
  }

  // Process a  statements to a SQL or File Action
  def process[R](statement: Statement): Option[ReturnableAction[R]] =
    statement match {
      case Stop => None
      case Help(string) => None // TODO: Add 'display help' action
      case SQL(query) => None // TODO: Add generic execute SQL action
      case Mono((request, objs)) => handleMonoRequest[R](request, objs)
      case Poly((request, superobj)) => handlePolyRequest[R](request, superobj)
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
}
