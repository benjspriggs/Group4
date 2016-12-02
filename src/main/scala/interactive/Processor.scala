package interactive

import interactive.action.{Action, ReturnableAction, SqlAction}
import sqldb.dbo.User

import scala.collection.mutable.ArrayBuffer

/**
  * Created by bspriggs on 11/30/2016.
  */
class Processor {
  // Process a sequence of statements to SQL or File Actions
  def process(statements: ArrayBuffer[_]) = Unit

  // Process a  statements to a SQL or File Action
  def process[R](statement: Statement): Option[ReturnableAction[R]] =
  statement match {
    case Stop => None
    case Help(string) => None // TODO: Add 'display help' action
    case SQL(query) => None // TODO: Add generic execute SQL action
    case Mono((request, objs)) => {
      None
    }
    case Poly((request, superobj)) => {
      None
    }
  }

  def loadClass(classname: String): Class[_] = {
    Class.forName("sqldb.dbo." + classname)
  }
}
