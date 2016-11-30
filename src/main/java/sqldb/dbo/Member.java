package sqldb.dbo;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by bspriggs on 11/29/2016.
 */
public class Member extends DatabaseObject {
    public final int number;
    public final String name;
    public boolean is_suspended;
    public final Location location;

    public Member(final Member m){
        number = m.number;
        name = m.name;
        location = m.location;
        is_suspended = m.is_suspended;
    }

    public Member(int number, String name, boolean is_suspended, Location location) {
        this.number = number;
        this.name = name;
        this.location = location;
        this.is_suspended = is_suspended;
    }

    @Override
    public String create() {
        return "CALL create_member(?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    public String show() {
        return "SELECT FROM member_view (number, is_suspended, name, city, state, street_address, zipcode)" +
                "WHERE number = ?";
    }

    @Override
    public String update() {
        return "CALL update_member(?, ?, ?, ?, ?, ?, ?);";
    }

    @Override
    public String delete() {
        return "DELETE FROM members WHERE number = ?;";
    }

    @Override
    public void fillStatement(DatabaseAction action, PreparedStatement statement) throws SQLException {
        switch (action) {
            case SHOW:
            case DELETE: statement.setInt(1, number); break;
            case CREATE:
            case UPDATE:
                statement.setInt(1, number);
                statement.setBoolean(2, is_suspended);
                statement.setString(3, name);
                statement.setString(4, location.street_address);
                statement.setString(5, location.city);
                statement.setString(6, location.state);
                statement.setString(7, location.zipcode);
                break;
        }
    }
}
