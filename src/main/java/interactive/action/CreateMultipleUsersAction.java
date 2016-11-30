package interactive.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by bspriggs on 11/29/2016.
 */
public class CreateMultipleUsersAction extends SqlBatchAction<String> {
    CreateMultipleUsersAction(Connection conn, HashMap<Integer, String> usernames){
        super(conn, "INSERT INTO user (username) VALUES ( ? );", usernames);
    }

    @Override
    protected void setStatements(Integer index) throws SQLException {
        preparedStatement.setString(index, this.map.get(index));
    }

    @Override
    public boolean isPrepared() {
        return true;
    }
}
