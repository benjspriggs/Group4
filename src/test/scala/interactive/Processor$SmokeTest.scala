package interactive

import java.sql.Connection

import interactive.Statements._
import interactive.Term._
import interactive.action.SqlAction
import org.scalatest.mockito.MockitoSugar
import sqldb.dbo.DatabaseObject.DatabaseAction
import sqldb.dbo.{Location, Member, Provider, User}

/**
  * Created by bspriggs on 11/30/2016.
  */
class Processor$SmokeTest extends InteractiveModeFixtures with MockitoSugar{

  behavior of "processor"
  val processor = new Processor()
  val connection: Connection = mock[Connection]

  it must "handle requests to create a user" in {
    val createUser = Mono((
      Request("create"),
      Obj((Type.One("user"), Seq(f.parsedJson("{ \"username\": \"test\" }")))))
    )
    val expectedSqlAction = new SqlAction[User](
      connection, new User(0, "test"), DatabaseAction.CREATE)
    assertResult(Some(expectedSqlAction))(processor.process(createUser))
  }

  it must "handle requests to create a member" in {
    val createMember = Mono((
      Request("create"),
      Obj((Type.One("member"), Seq(
        f.parsedJson(
          """
            | {
            | "name": "name",
            | "is_suspended": false,
            | "street_address": "street_address",
            | "city": "city",
            | "state": "state",
            | "zipcode": "zipcode"
            | }
          """.stripMargin)
      )) )
    ))
    val location = new Location(0, "street_address", "city", "state", "zipcode")
    val expectedSqlAction = new SqlAction[Member](
      connection, new Member(0, "name", false, location), DatabaseAction.CREATE)
    assertResult(Some(expectedSqlAction))(processor.process(createMember))
  }

  it must "handle requests to delete a provider" in {
    val deleteProvider = Mono((
      Request("delete"),
      Obj((Type.One("provider"), Seq(
        f.parsedJson(
          """
            | {
            | "name": "name",
            | "is_suspended": false,
            | "street_address": "street_address",
            | "city": "city",
            | "state": "state",
            | "zipcode": "zipcode"
            | }
          """.stripMargin)
      ))))
    )

    val location = new Location(0, "street_address", "city", "state", "zipcode")
    val expectedSqlAction = new SqlAction[Provider](
      connection, new Provider(0, "name", location), DatabaseAction.CREATE)
    assertResult(Some(expectedSqlAction))(processor.process(deleteProvider))
  }

  it must "handle requests to stop" in {
    assertResult(None)(processor.process(Stop))
  }
}
