package interactive.action;

import sqldb.dbo.DatabaseObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by bspriggs on 11/29/2016.
 */
public class SqlAction<V extends DatabaseObject> implements ReturnableAction<ResultSet> {
    private Connection connection;
    private V value;
    private PreparedStatement statement;
    private final DatabaseObject.DatabaseAction action;

    protected SqlAction(Connection c, V value, DatabaseObject.DatabaseAction action)
    {
        connection = c;
        this.value = value;
        this.action = action;
        statement = null;
    }

    @Override
    public void execute(){
        executeAndReturn();
    }

    @Override
    public ResultSet executeAndReturn(){
        ResultSet r = null;
        try {
            if (statement == null)
                this.statement = value.prepareStatement(action, connection);
            connection.setAutoCommit(false);
            value.fillStatement(action, statement);
            statement.executeQuery();
            r = statement.getResultSet();
        } catch (SQLException e) {
            System.err.println("An error occurred preparing the statement");
            e.printStackTrace();
        } finally {
            try {
                connection.commit();
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    System.err.println("An error occurred rolling back the changes");
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
        }
        return r;
    }

}
