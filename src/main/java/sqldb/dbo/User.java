package sqldb.dbo;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by bspriggs on 11/29/2016.
 */
public class User extends DatabaseObject {
    private final int id;
    private final String username;

    public User(int id, String username) {
        this.id = id;
        this.username = username;
    }

    @Override
    public String create() {
        return "INSERT INTO users (username) VALUES (?);";
    }

    @Override
    public String show() {
        return "SELECT * FROM users WHERE id = ?;";
    }

    @Override
    public String update() {
        return "UPDATE users SET username = ? WHERE id = ?;";
    }

    @Override
    public String delete() {
        return "DELETE FROM users WHERE id = ?;";
    }

    @Override
    public void fillStatement(DatabaseAction action, PreparedStatement statement) throws SQLException {
        switch (action) {
            case SHOW:
            case DELETE:
                statement.setInt(1, id); break;
            case CREATE:
                statement.setString(1, username); break;
            case UPDATE:
                statement.setString(1, username);
                statement.setInt(2, id); break;
        }
    }
}
