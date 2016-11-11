package sqldb;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by bspriggs on 11/10/2016.
 */
public abstract class SqlDatabaseSchemaTest {
    protected abstract SqlDatabaseSchema getSqlDatabaseSchema();

    private SqlDatabaseSchema testSchema;

    @Before
    public void setUp() throws Exception {
        testSchema = getSqlDatabaseSchema();
    }

    @Test
    public void createTableStatement() throws Exception {
        assertNotNull(testSchema.createTableStatement());
    }

    @Test
    public void schemaVersion() throws Exception {
        assertTrue(testSchema.schemaVersion() > 0);
    }

}