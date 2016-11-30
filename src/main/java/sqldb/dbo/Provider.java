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
        return "CALL create_providers(?, ?, ?, ?, ?, ?);";
    }

    @Override
    protected String show() {
        return "SELECT * FROM provider_view WHERE number = ?";
    }

    // TODO: Make a procedure that does this
    @Override
    protected String update() {
        return "CALL update_provider(?);";
    }

    @Override
    protected String delete() {
        return "DELETE FROM providers WHERE number = ?;";
    }

    @Override
    void fillStatement(DatabaseAction action, PreparedStatement statement) throws SQLException {
        switch (action) {
            case SHOW:
            case DELETE: statement.setInt(1, number);
            case CREATE:
            case UPDATE:
                statement.setInt(1, number);
                statement.setString(2, name);
                statement.setString(3, location.street_address);
                statement.setString(4, location.city);
                statement.setString(5, location.state);
                statement.setString(6, location.zipcode);
        }
    }
}
