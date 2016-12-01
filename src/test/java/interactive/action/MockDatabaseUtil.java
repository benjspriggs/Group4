package interactive.action;

import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.junit.runner.RunWith;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.verify;

/**
 * Created by bspriggs on 11/30/2016.
 */
@RunWith(EasyMockRunner.class)
abstract public class MockDatabaseUtil {
    @Mock
    protected Connection connection;
    @Mock
    protected ResultSet resultSet;
    @Mock
    protected PreparedStatement preparedStatement;

    public void setExpectations(String...queries) throws Exception{
        for (String query: queries){
            expect(connection.prepareStatement(query)).andReturn(preparedStatement);
        }
        expect(preparedStatement.executeQuery()).andReturn(resultSet).anyTimes();
    }

    public void verifyMocks() throws Exception {
        verify(connection);
        verify(resultSet);
        verify(preparedStatement);
    }

    public void closeResources() throws Exception {
        preparedStatement.close();
        resultSet.close();
    }

    public Connection getConnection() {
        return connection;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }
}
