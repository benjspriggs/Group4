package interactive

import interactive.Term._
import interactive.Statements._
import org.scalatest.FlatSpec

/**
  * Created by bspriggs on 11/30/2016.
  */
class Processor$SmokeTest extends InteractiveModeFixtures {

  behavior of "processor"

  ignore must "handle requests to create a user" in
  {
      val createUser = Mono((
        Request("create"),
          Obj((Type.One("user"), Seq(f.parsedJson())))
        )
      )
  }

  ignore must "handle requests to create a member" in
  {
    val createMember = Mono((
      Request("create"),
      Obj((Type.One("member"), Seq(f.parsedJson()))) )
    )
  }

  ignore must "handle requests to delete a service" in
  {
    val deleteService = Mono((
      Request("delete"),
      Obj((Type.One("service"), Seq(f.parsedJson()))))
    )
  }

  ignore must "handle requests to stop" in {

  }
}
