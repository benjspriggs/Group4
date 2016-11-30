package sqldb.dbo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by bspriggs on 11/29/2016.
 */
abstract public class DatabaseObject {
    enum DatabaseAction{
        CREATE, SHOW,
        UPDATE, DELETE
    }
    abstract protected String create();
    abstract protected String show();
    abstract protected String update();
    abstract protected String delete();
    abstract void fillStatement(DatabaseAction action, PreparedStatement statement) throws SQLException;

    PreparedStatement prepareStatement(DatabaseAction action, Connection conn) throws SQLException {
        PreparedStatement p = null;
        switch (action){
            case CREATE: p = conn.prepareStatement(create());
            case SHOW: p = conn.prepareStatement(show());
            case UPDATE: p = conn.prepareStatement(update());
            case DELETE: p = conn.prepareStatement(delete());
        }
        return p;
    }
}
