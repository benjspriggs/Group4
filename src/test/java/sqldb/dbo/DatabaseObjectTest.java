package sqldb.dbo;

import org.easymock.Mock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.easymock.EasyMock.*;

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
        assert getImplementation().create() != null;
    }

    @Test
    public void show() throws Exception {
        assert getImplementation().show() != null;
    }

    @Test
    public void update() throws Exception {
        assert getImplementation().update() != null;
    }

    @Test
    public void delete() throws Exception {
        assert getImplementation().delete() != null;
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
}