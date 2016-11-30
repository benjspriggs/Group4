package interactive.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by bspriggs on 11/29/2016.
 */
abstract public class SqlBatchAction<V> implements ReturnableAction<ResultSet> {
    protected Connection connection;
    protected HashMap<Integer, V> map;
    protected PreparedStatement preparedStatement;

    protected SqlBatchAction(Connection c, final String statement, HashMap<Integer, V> map) {
        connection = c;
        this.map = map;
        preparedStatement = prepareStatement(statement);
    }

    abstract protected void setStatements(final Integer index) throws SQLException;

    protected PreparedStatement prepareStatement(final String s) {
        try {
            return connection.prepareStatement(s);
        } catch (SQLException e) {
            System.err.println("An error occurred preparing the statement in SqlAction");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void execute(){
        executeAndReturn();
    }

    @Override
    public ResultSet executeAndReturn() {
        ResultSet r = null;
        try {
            connection.setAutoCommit(false);
            for (Integer i = 0; i < map.size(); ++i) {
                setStatements(i);
                preparedStatement.executeQuery();
            }
            r = preparedStatement.getResultSet();
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
