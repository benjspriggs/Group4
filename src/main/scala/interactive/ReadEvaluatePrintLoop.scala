package interactive

import java.io._

import fastparse.core.Parsed
import fastparse.core.Parsed.{Failure, Success}
import interactive.Statements._
import interactive.parser.Parser

/**
  * Created by bspriggs on 11/15/2016.
  */
class ReadEvaluatePrintLoop {
  private lazy val parser = Parser.expression

  def parseInput(s: String) = parser.parse(s)

  def continue(t: Parsed[Any, Char, String]): Boolean = t match {
    case s: Parsed.Success[Any, Char, String] => s match {
      case Parsed.Success(Stop, _) => false
      case _ => true
    }
    case _ => true
  }

  def helpMessage(t: Parsed[Any, Char, String]) = t match {
    case f: Failure[Char, String] => "Error, unrecognized token at: " + f.msg
    case s: Success[Any, Char, String] => "Success! " + s
  }

  def start(input: InputStream, output: OutputStream) = {
    // Run the REPL
    val reader = new BufferedReader(new InputStreamReader(input))
    val writer = new BufferedWriter(new OutputStreamWriter(output))
    Stream.continually(reader readLine)
      .takeWhile(_ != null)
      .map(parser.parse(_))
      .takeWhile(continue)
      .foreach((s: Parsed[Any, Char, String]) => { writer write helpMessage(s); writer flush();})
  }
}
