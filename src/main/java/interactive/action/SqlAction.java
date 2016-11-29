package interactive.action;

import java.sql.Connection;

/**
 * Created by bspriggs on 11/29/2016.
 */
abstract public class SqlAction implements Action {
    private Connection connection;
    SqlAction(Connection c)
    {
        connection = c;
    }
    abstract public void execute();
}
