package sqldb.dbo;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by bspriggs on 11/29/2016.
 */
public class Member extends DatabaseObject {
    public final String number;
    public final String name;
    public boolean is_suspended;
    public final Location location;

    protected final String createString =
            "CALL create_member(?, ?, ?, ?, ?, ?)";
    protected final String showString =
            "SELECT FROM member_view (number, is_suspended, name, city, state, street_address, zipcode)" +
                    "WHERE number = ?";
    protected final String updateString =
            "UPDATE member" +
                    "JOIN member_info using (number)" +
                    "JOIN locations_lookup ON locations_lookup.member_number = members.number" +
                    "JOIN locations ON locations.id = locations_lookup.location_id" +
                    "SET name = ?," +
                    "is_suspended = ?," +
                    "street_address = ?," +
                    "city = ?," +
                    "state = ?," +
                    "zipcode = ?;";
    protected final String deleteString =
            "DELETE FROM members WHERE number = ?";

    public Member(final Member m){
        number = m.number;
        name = m.name;
        location = m.location;
        is_suspended = m.is_suspended;
    }

    public Member(String number, String name, boolean is_suspended, Location location) {
        this.number = number;
        this.name = name;
        this.location = location;
        this.is_suspended = is_suspended;
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

    @Override
    public void fillStatement(DatabaseAction action, PreparedStatement statement) throws SQLException {
        switch (action) {
            case CREATE:
            case SHOW:
            case UPDATE:
            case DELETE:
        }
    }
}
