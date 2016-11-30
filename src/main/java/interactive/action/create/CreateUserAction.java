package interactive.action.create;

import interactive.action.SqlAction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by bspriggs on 11/29/2016.
 */
public class CreateUserAction extends SqlAction<String> {
    protected final static String createUserString = "INSERT INTO users (USERNAME) VALUES ( ? );";

    public CreateUserAction(Connection c, final String username) {
        super(c, createUserString, "INSERT INTO users (USERNAME) VALUES ( ? );");
    }

    public boolean isPrepared() {
        return true;
    }

    @Override
    protected void setStatement(PreparedStatement s) throws SQLException {
        s.setString(1, super.value);
    }
}
