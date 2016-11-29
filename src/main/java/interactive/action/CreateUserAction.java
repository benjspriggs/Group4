package interactive.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by bspriggs on 11/29/2016.
 */
final public class CreateUserAction extends SqlAction {
    private PreparedStatement createUserStatement;
    private String username;
    private final String createUserString = "INSERT INTO users (USERNAME) VALUES ( ? );";

    public CreateUserAction(Connection c, final String u) {
        super(c);
        username = u;
        createUserStatement = prepareStatement(createUserString);
    }

    public boolean isPrepared() {
        return true;
    }

    public void execute(){
        try {
            connection.setAutoCommit(false);
            createUserStatement.setString(1, username);
            createUserStatement.execute();
            connection.commit();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
