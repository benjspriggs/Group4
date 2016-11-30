package interactive.action;

import com.mockrunner.jdbc.BasicJDBCTestCaseAdapter;
import com.mockrunner.jdbc.StatementResultSetHandler;
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

    private void prepareEmptyResultSet(){
        MockConnection connection = getJDBCMockObjectFactory().getMockConnection();
        StatementResultSetHandler statementHandler =
                connection.getStatementResultSetHandler();
        MockResultSet resultSet = statementHandler.createResultSet();
        statementHandler.prepareGlobalResultSet(resultSet);
        mockConnection = connection;
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void executeShowNonexistentUser() throws Exception {
        prepareEmptyResultSet();
        SqlAction<User> showUserAction = new SqlAction<>(
                mockConnection,
                new User(0, "username"),
                DatabaseObject.DatabaseAction.CREATE
                );
        showUserAction.execute();
        mockConnection.close();
        verifyCommitted();
        verifyNotRolledBack();
    }

    @Test
    public void executeAndReturn() throws Exception {

    }

}