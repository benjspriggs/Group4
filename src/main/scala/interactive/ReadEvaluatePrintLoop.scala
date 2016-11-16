package interactive

import java.io._
import java.util.function.Consumer

import fastparse.core.Parsed
import fastparse.core.Parsed.{Failure, Success}
import interactive.parser.Parser

import scala.Serializable

/**
  * Created by bspriggs on 11/15/2016.
  */
class ReadEvaluatePrintLoop {
  private lazy val parser = Parser.expr

  def parseInput(s: String) = parser.parse(s)

  def continue(t: Parsed[Product with Serializable, Char, String]): Boolean = t match {
    case _: Success[Product with Serializable, Char, String] => false
    case _ => true
  }

  def helpMessage(t: Parsed[Product with Serializable, Char, String]) = t match {
    case f: Failure[Char, String] => "Error, unrecognized token at: " + f.msg
    case s: Success[Product with Serializable, Char, String] => "Success! " + s
  }

  def start(input: InputStream, output: OutputStream) = {
    // Run the REPL
    val reader = new BufferedReader(new InputStreamReader(input))
    val writer = new BufferedWriter(new OutputStreamWriter(output))
    Stream.continually(reader readLine)
      .takeWhile(_ != null)
      .map(parser.parse(_))
      .takeWhile(continue)
      .foreach((s: Parsed[Product with Serializable, Char, String]) => { writer write helpMessage(s); writer flush();})
  }
}
