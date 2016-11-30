package sqldb.dbo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by bspriggs on 11/29/2016.
 */
public interface DatabaseObject {
    enum DatabaseAction{
        CREATE, SHOW,
        UPDATE, DELETE
    }
    String create();
    String show();
    String update();
    String delete();
    void fillStatement(DatabaseAction action, PreparedStatement statement) throws SQLException;
    PreparedStatement prepareStatement(DatabaseAction action, Connection conn) throws SQLException;
}
