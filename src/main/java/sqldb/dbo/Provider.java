package sqldb.dbo;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by bspriggs on 11/29/2016.
 */
public class Provider extends DatabaseObject {
    private final int number;
    private final String name;
    private final Location location;

    public Provider(int number, String name, Location location) {
        this.number = number;
        this.name = name;
        this.location = location;
    }

    @Override
    protected String create() {
        return null;
    }

    @Override
    protected String show() {
        return null;
    }

    @Override
    protected String update() {
        return null;
    }

    @Override
    protected String delete() {
        return null;
    }

    @Override
    void fillStatement(DatabaseAction action, PreparedStatement statement) throws SQLException {

    }
}
