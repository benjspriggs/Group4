import scala.util.parsing.combinator.RegexParsers

/**
  * Created by bspriggs on 11/10/2016.
  */


object InteractiveModeLexer extends RegexParsers {
  // Interactive mode is whitespace-insensitive
  override def skipWhitespace = true
  override val whiteSpace = "[ \t\f\r]+".r

  // lexers for all the literals
  def exit = "exit" ^^ (_ => EXIT)
  def quit = "quit" ^^ (_ => QUIT)
  def help = "help" ^^ (_ => HELP)
  def question_mark = "?" ^^ (_ => QUESTION_MARK)
  def create = "create" ^^ (_ => CREATE)
  def show = "show" ^^ (_ => SHOW)
  def update = "update" ^^ (_ => UPDATE)
  def delete = "delete" ^^ (_ => DELETE)
  def write = "write" ^^ (_ => WRITE)
  def all = "all" ^^ (_ => ALL)
  def sql_literal = "SQL" ^^ (_ => SQL_LITERAL)
  // chocan related
  def user = "user" ^^ (_ => USER)
  def users = "users" ^^ (_ => USERS)
  def provider = "provider" ^^ (_ => PROVIDER)
  def providers = "providers" ^^ (_ => PROVIDERS)
  def service = "service" ^^ (_ => SERVICE)
  def services = "services" ^^ (_ => SERVICES)
  def member = "member" ^^ (_ => MEMBER)
  def members = "members" ^^ (_ => MEMBERS)

}
