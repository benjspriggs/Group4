package sqldb.schemas;

import sqldb.SqlDatabaseSchema;
import sqldb.SqlDatabaseSchemaTest;

/**
 * Created by bspriggs on 11/10/2016.
 */
public class UserSchemaTest extends SqlDatabaseSchemaTest {
    protected SqlDatabaseSchema getSqlDatabaseSchema(){
        return new UserSchema();
    }
}