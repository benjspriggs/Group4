package interactive

import interactive.action.{ReturnableAction, SqlAction}
import sqldb.dbo.User

import scala.collection.mutable.ArrayBuffer

/**
  * Created by bspriggs on 11/30/2016.
  */
class Processor {
  // Process a sequence of statements to SQL or File Actions
  def process(trie: ArrayBuffer[_]) = Unit

  // Process a  statements to a SQL or File Action
  def process[R, V](statements: Product with Serializable): Option[ReturnableAction[User]] =
  {
    Some(
      new SqlAction[User](null, null, null)
        .asInstanceOf[ReturnableAction[User]])
  }
}
