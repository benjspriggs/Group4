/**
  * Created by bspriggs on 11/10/2016.
  */

sealed trait Token

// Token classes
case class STOP(str: String) extends Token
case class INFO(str: String) extends Token
case class REQUEST(str: String) extends Token
case class TYPE(str: String) extends Token
case class JSON(json: String) extends Token
case class SQL(sql: String) extends Token



object InteractiveModeLexer {

}
