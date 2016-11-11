package sqldb.schemas;

import sqldb.SqlDatabaseSchema;


/**
 * Created by bspriggs on 11/10/2016.
 */
public class UserSchema implements SqlDatabaseSchema {
    private final static int VERSION = 1;
    private final static String SQL =
            "CREATE TABLE users (" +
            "id         INT PRIMARY KEY NOT NULL AUTO_INCREMENT," +
            "username   VARCHAR(40) NOT NULL," +
            "last_login TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP" +
            ");";

    public String createTableStatement() {
        return SQL;
    }

    public int schemaVersion() {
        return VERSION;
    }
}
