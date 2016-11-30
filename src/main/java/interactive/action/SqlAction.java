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
        }
    }

    abstract protected void setStatement(PreparedStatement s);
    abstract protected void setStatements(PreparedStatement s, final int index);

    protected void executeSingleStatement(PreparedStatement s){
        try {
            connection.setAutoCommit(false);
            setStatement(s);
            s.execute();
        } catch (SQLException e){
            System.err.println("An error occurred preparing the statement");
            e.printStackTrace();
        } finally {
            try {
                connection.commit();
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected void executeBatchStatement(PreparedStatement s, int count){
        try {
            connection.setAutoCommit(false);
            for (int i = 0; i < count; ++i){
                setStatements(s, i);
                s.execute();
            }
        } catch (SQLException e){
            System.err.println("An error occurred preparing the statement");
            e.printStackTrace();
        } finally {
            try {
                connection.commit();
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
