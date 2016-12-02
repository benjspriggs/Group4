package interactive

import interactive.Term.{Obj, Request, SuperObj}
import interactive.action._

import scala.collection.mutable.ArrayBuffer

/**
  * Created by bspriggs on 11/30/2016.
  */
class Processor {
  // Process a sequence of statements to SQL or File Actions
  def process(statements: ArrayBuffer[_]) = Unit

  private def handleMonoRequest[R](request: Request, objs: Obj):
    Option[ReturnableAction[R]] = {
    None
  }

  private def handlePolyRequest[R](request: Request, superobj: SuperObj):
    Option[ReturnableAction[R]] = {
    None
  }

  // Process a  statements to a SQL or File Action
  def process[R](statement: Statement): Option[ReturnableAction[R]] =
    statement match {
      case Stop => None
      case Help(string) => None // TODO: Add 'display help' action
      case SQL(query) => None // TODO: Add generic execute SQL action
      case Mono((request, objs)) => {
        handleMonoRequest[R](request, objs)
      }
      case Poly((request, superobj)) => {
        handlePolyRequest[R](request, superobj)
      }
    }

  def loadClass(classname: String): Class[_] = {
    Class.forName("sqldb.dbo." + classname)
  }


}
