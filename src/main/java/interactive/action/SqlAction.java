package interactive.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by bspriggs on 11/29/2016.
 */
abstract public class SqlAction<V> implements Action {
    protected Connection connection;
    protected V value;
    protected PreparedStatement statement;

    protected SqlAction(Connection c, V value, final String statementString)
    {
        connection = c;
        this.value = value;
        this.statement = prepareStatement(statementString);
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

    abstract protected void setStatement(PreparedStatement s) throws SQLException;

    @Override
    public void execute(){
        try {
            connection.setAutoCommit(false);
            setStatement(statement);
            statement.execute();
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
