package interactive.action;

import com.mockrunner.jdbc.BasicJDBCTestCaseAdapter;
import com.mockrunner.jdbc.PreparedStatementResultSetHandler;
import com.mockrunner.mock.jdbc.MockConnection;
import com.mockrunner.mock.jdbc.MockResultSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sqldb.dbo.DatabaseObject;
import sqldb.dbo.User;

import java.sql.Connection;

/**
 * Created by bspriggs on 11/29/2016.
 */
public class SqlActionTest extends BasicJDBCTestCaseAdapter {
    private Connection mockConnection;
    @Before
    public void setUp() throws Exception {

    }

    private void prepareSingleUserResultSet(){
        MockConnection connection = getJDBCMockObjectFactory().getMockConnection();
        PreparedStatementResultSetHandler statementHandler =
                connection.getPreparedStatementResultSetHandler();
        MockResultSet resultSet = statementHandler.createResultSet();
        resultSet.addColumn("id", new Object[] { "0"});
        resultSet.addColumn("username", new Object[] { "username"});
        statementHandler.prepareResultSet("insert into", resultSet);
        mockConnection = connection;
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void executeShowUser() throws Exception {
        MockConnection connection = getJDBCMockObjectFactory().getMockConnection();
        PreparedStatementResultSetHandler statementHandler =
                connection.getPreparedStatementResultSetHandler();
        MockResultSet resultSet = statementHandler.createResultSet();
        resultSet.addColumn("id", new Object[] { "0"});
        resultSet.addColumn("username", new Object[] { "username"});
        statementHandler.prepareResultSet("insert into", resultSet);
        SqlAction<User> showUserAction = new SqlAction<>(
                connection,
                new User(0, "username"),
                DatabaseObject.DatabaseAction.CREATE
                );
        showUserAction.execute();
        //verifySQLStatementExecuted("insert into");
    }

    @Test
    public void executeAndReturn() throws Exception {

    }

}