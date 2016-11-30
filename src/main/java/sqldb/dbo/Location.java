package sqldb.dbo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by bspriggs on 11/29/2016.
 */
public class Location implements DatabaseObject{
    public final int id;
    public final String street_address;
    public final String city;
    public final String state;
    public final String zipcode;
    protected final String createString =
            "INSERT INTO locations (street_address, city, state, zipcode) " +
            "VALUES (?, ?, ?, ?);";
    protected final String showString =
            "SELECT * FROM locations WHERE id = ?;";
    protected final String updateString =
            "UPDATE locations " +
                    "SET street_address = ?," +
                    "city = ?," +
                    "state = ?," +
                    "zipcode = ?" +
                    " WHERE id = ?;";
    protected final String deleteString =
            "DELETE FROM locations WHERE id = ?;";

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
    public PreparedStatement prepareStatement(DatabaseAction action, Connection conn) throws SQLException {
        PreparedStatement p = null;
        switch (action){
            case CREATE: p = conn.prepareStatement(create());
            case SHOW: p = conn.prepareStatement(show());
            case UPDATE: p = conn.prepareStatement(update());
            case DELETE: p = conn.prepareStatement(delete());
        }
        return p;
    }

    @Override
    public String create() {
        return createString;
    }

    @Override
    public String show() {
        return showString;
    }

    @Override
    public String update() {
        return updateString;
    }

    @Override
    public String delete() {
        return deleteString;
    }
}
