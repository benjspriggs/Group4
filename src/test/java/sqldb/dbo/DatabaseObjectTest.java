package sqldb.dbo;

import org.easymock.Mock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by bspriggs on 11/30/2016.
 */
abstract public class DatabaseObjectTest<T extends DatabaseObject> {
    abstract protected DatabaseObject getImplementation();
    abstract protected Class<T> getImplementationClass();
    @Mock
    protected PreparedStatement preparedStatement;

    @Before
    public void setUp() throws Exception {
        preparedStatement = createMock(PreparedStatement.class);
    }

    @Test
    public void create() throws Exception {
        assertNotNull(getImplementation().create());
        assertEndingSemicolon(getImplementation().create());
    }

    @Test
    public void show() throws Exception {
        assertNotNull(getImplementation().show());
        assertEndingSemicolon(getImplementation().show());
    }

    @Test
    public void update() throws Exception {
        assertNotNull(getImplementation().update());
        assertEndingSemicolon(getImplementation().update());
    }

    @Test
    public void delete() throws Exception {
        assertNotNull(getImplementation().delete());
        assertEndingSemicolon(getImplementation().delete());
    }

    @Test
    public void fillStatement() throws Exception {
        for (DatabaseObject.DatabaseAction a : DatabaseObject.DatabaseAction.values()){
            getImplementation().fillStatement(a, preparedStatement);
        }
    }

    public void prepareStatement(DatabaseObject.DatabaseAction action, Method m) throws Exception {
        DatabaseObject object = getImplementation();
        Connection mockConn = createMock(Connection.class);
        expect(mockConn.prepareStatement((String) m.invoke(object))).andStubReturn(preparedStatement);
        replay(mockConn);
        PreparedStatement expected = mockConn.prepareStatement((String) m.invoke(object));
        Assert.assertSame(expected,
                object
                        .prepareStatement(action, mockConn));
    }

    @Test
    public void prepareCreateStatement() throws Exception {
        prepareStatement(DatabaseObject.DatabaseAction.CREATE, getImplementationClass().getMethod("create"));
    }

    @Test
    public void prepareShowStatement() throws Exception {
        prepareStatement(DatabaseObject.DatabaseAction.SHOW, getImplementationClass().getMethod("show"));
    }

    @Test
    public void prepareUpdateStatement() throws Exception {
        prepareStatement(DatabaseObject.DatabaseAction.UPDATE, getImplementationClass().getMethod("update"));
    }

    @Test
    public void prepareDeleteStatement() throws Exception {
        prepareStatement(DatabaseObject.DatabaseAction.DELETE, getImplementationClass().getMethod("delete"));
    }

    private void assertEndingSemicolon(String s) {
        assertEquals("The SQL statement should end with a ';'",
                ";",
                s.substring(s.length() - 1));
    }

}