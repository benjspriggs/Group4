package sqldb.dbo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by bspriggs on 11/29/2016.
 */
abstract public class DatabaseObject {
    public enum DatabaseAction{
        CREATE, SHOW,
        UPDATE, DELETE
    }
    abstract public String create();
    abstract public String show();
    abstract public String update();
    abstract public String delete();
    abstract public void fillStatement(DatabaseAction action, PreparedStatement statement) throws SQLException;

    public PreparedStatement prepareStatement(DatabaseAction action, Connection conn) throws SQLException {
        PreparedStatement p = null;
        switch (action){
            case CREATE: p = conn.prepareStatement(create()); break;
            case SHOW: p = conn.prepareStatement(show()); break;
            case UPDATE: p = conn.prepareStatement(update()); break;
            case DELETE: p = conn.prepareStatement(delete()); break;
        }
        return p;
    }
}
