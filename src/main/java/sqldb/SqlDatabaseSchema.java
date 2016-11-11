package sqldb;

import com.sun.istack.internal.NotNull;

/**
 * Created by bspriggs on 11/10/2016.
 */
public interface SqlDatabaseSchema {
    @NotNull
    String createTableStatement();
    int schemaVersion();
}
