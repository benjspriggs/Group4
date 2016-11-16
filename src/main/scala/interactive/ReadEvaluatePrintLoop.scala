package interactive

import java.io._
import java.util.function.Consumer

import fastparse.core.Parsed
import interactive.parser.Parser

/**
  * Created by bspriggs on 11/15/2016.
  */
class ReadEvaluatePrintLoop {
  private lazy val parser = Parser.expr

  def parseInput(s: String) = parser.parse(s)

  def start(input: InputStream, output: OutputStream) = {
    // Run the repl loop
    val reader = new BufferedReader(new InputStreamReader(input))
    val writer = new BufferedWriter(new OutputStreamWriter(output))
    Stream.continually(reader readLine)
      .takeWhile(_ != null)
      .map(parser.parse(_))
      .foreach((s: Parsed[Product with Serializable,_,_]) => { writer write s.toString; writer flush();})
  }
}
