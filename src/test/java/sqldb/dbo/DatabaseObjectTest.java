package sqldb.dbo;

import org.junit.Test;

/**
 * Created by bspriggs on 11/30/2016.
 */
abstract public class DatabaseObjectTest {
    abstract protected DatabaseObject getImplementation();
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

    }

    @Test
    public void prepareStatement() throws Exception {

    }

}