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
case class OBJECT(str: String) extends Token
case class SUPER_OBJECT(str: String) extends Token

// Reserved words
case object QUIT extends Token
case object EXIT extends Token
case object HELP extends Token
case object QUESTION_MARK extends Token
case object CREATE extends Token
case object SHOW extends Token
case object UPDATE extends Token
case object DELETE extends Token
case object WRITE extends Token
case object ALL extends Token
case object SQL_LITERAL extends Token

// ChocAn-related reserved words
case object USER extends Token
case object USERS extends Token
case object MEMBER extends Token
case object MEMBERS extends Token
case object PROVIDER extends Token
case object PROVIDERS extends Token
case object SERVICE extends Token
case object SERVICES extends Token
