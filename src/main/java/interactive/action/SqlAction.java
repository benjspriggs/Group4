package interactive.action;

import sqldb.dbo.DatabaseObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by bspriggs on 11/29/2016.
 */
abstract public class SqlAction<V extends DatabaseObject> implements ReturnableAction<ResultSet> {
    protected Connection connection;
    protected V value;
    protected PreparedStatement statement;
    private final DatabaseObject.DatabaseAction action;

    protected SqlAction(Connection c, V value, DatabaseObject.DatabaseAction action)
    {
        connection = c;
        this.value = value;
        this.action = action;
    }

    abstract protected void setStatement(PreparedStatement s) throws SQLException;

    @Override
    public void execute(){
        executeAndReturn();
    }

    @Override
    public ResultSet executeAndReturn(){
        ResultSet r = null;
        try {
            this.statement = value.prepareStatement(action, connection);
            connection.setAutoCommit(false);
            setStatement(statement);
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
