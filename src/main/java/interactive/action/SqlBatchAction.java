package interactive.action;

import sqldb.dbo.DatabaseObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by bspriggs on 11/29/2016.
 */
public class SqlBatchAction<V extends DatabaseObject> implements ReturnableAction<ResultSet> {
    private Connection connection;
    private List<V> items;
    private PreparedStatement preparedStatement;
    private final DatabaseObject.DatabaseAction action;

    protected SqlBatchAction(Connection c, List<V> items, DatabaseObject.DatabaseAction action) {
        connection = c;
        this.items = items;
        this.action = action;
        preparedStatement = null;
    }

    @Override
    public void execute(){
        executeAndReturn();
    }

    @Override
    public ResultSet executeAndReturn() {
        if (items.isEmpty())
            return null;
        ResultSet r = null;
        try {
            if (preparedStatement == null)
                preparedStatement = items.get(0).prepareStatement(action, connection);
            connection.setAutoCommit(false);
            for (V item : items) {
                item.fillStatement(action, preparedStatement);
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
