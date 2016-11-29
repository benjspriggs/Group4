package interactive.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by bspriggs on 11/29/2016.
 */
abstract public class SqlAction implements Action {
    protected Connection connection;
    SqlAction(Connection c)
    {
        connection = c;
    }

    protected PreparedStatement prepareStatement(final String s){
        try {
            return connection.prepareStatement(s);
        } catch (SQLException e) {
            System.err.println("An error occurred preparing the statement in SqlAction");
            e.printStackTrace();
            return null;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("An error occurred setting the autocommit status in SqlAction");
                e.printStackTrace();
            }
        }
    }
}
