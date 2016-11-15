package sqldb;

/**
 * Created by bspriggs on 11/10/2016.
 */
public interface SqlDatabaseSchema {
    @org.jetbrains.annotations.NotNull
    String createTableStatement();
    int schemaVersion();
}
