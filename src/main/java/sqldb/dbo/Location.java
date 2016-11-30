package sqldb.dbo;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by bspriggs on 11/29/2016.
 */
public class Location extends DatabaseObject{
    public final int id;
    public final String street_address;
    public final String city;
    public final String state;
    public final String zipcode;

    public Location(final Location l){
        id = l.id;
        street_address = l.street_address;
        city = l.city;
        state = l.state;
        zipcode = l.zipcode;
    }

    public Location(int id, String street_address, String city, String state, String zipcode) {
        this.id = id;
        this.street_address = street_address;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
    }

    @Override
    public void fillStatement(DatabaseAction action, PreparedStatement statement) throws SQLException {
        statement.clearParameters();
        switch (action){
            case DELETE:
            case SHOW:
                statement.setInt(1, id);
                break;
            case UPDATE:
                statement.setInt(5, id);
            case CREATE:
                statement.setString(1, street_address);
                statement.setString(2, city);
                statement.setString(3, state);
                statement.setString(4, zipcode);
                break;
        }
    }

    @Override
    protected String create() {
        return "INSERT INTO locations (street_address, city, state, zipcode) " +
                "VALUES (?, ?, ?, ?);";
    }

    @Override
    protected String show() {
        return "SELECT * FROM locations WHERE id = ?;";
    }

    @Override
    protected String update() {
        return "UPDATE locations " +
                "SET street_address = ?," +
                "city = ?," +
                "state = ?," +
                "zipcode = ?" +
                " WHERE id = ?;";
    }

    @Override
    protected String delete() {
        return "DELETE FROM locations WHERE id = ?;";
    }
}
