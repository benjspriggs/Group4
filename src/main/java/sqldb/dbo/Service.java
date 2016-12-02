package sqldb.dbo;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by bspriggs on 12/1/2016.
 */
public class Service extends DatabaseObject {
    private final int service_code;
    private final String name;

    @Override
    public String create() {
        return null;
    }

    @Override
    public String show() {
        return null;
    }

    @Override
    public String update() {
        return null;
    }

    @Override
    public String delete() {
        return null;
    }

    @Override
    public void fillStatement(DatabaseAction action, PreparedStatement statement) throws SQLException {

    }
}
