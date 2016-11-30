package sqldb.dbo;

import org.easymock.Mock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.easymock.EasyMock.*;

/**
 * Created by bspriggs on 11/30/2016.
 */
abstract public class DatabaseObjectTest {
    abstract protected DatabaseObject getImplementation();
    @Mock
    PreparedStatement preparedStatement;

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

    @Test
    public void prepareCreateStatement() throws Exception {
        DatabaseObject object = getImplementation();
        Connection mockConn = createMock(Connection.class);
        expect(mockConn.prepareStatement(object.create())).andStubReturn(preparedStatement);
        replay(mockConn);
        PreparedStatement expected = mockConn.prepareStatement(object.create());
        Assert.assertSame(expected,
                object
                        .prepareStatement(DatabaseObject.DatabaseAction.CREATE, mockConn));
    }

}