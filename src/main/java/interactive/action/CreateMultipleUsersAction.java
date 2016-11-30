package interactive.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by bspriggs on 11/29/2016.
 */
public class CreateMultipleUsersAction extends CreateUserAction {
    private List<String> usernames;

    CreateMultipleUsersAction(Connection conn, List<String> usernames){
        super(conn);
        this.usernames = usernames;
    }

    @Override
    public void execute(){
        try {
            connection.setAutoCommit(false);
            for (String user : usernames) {
                createUserStatement.setString(1, user);
                createUserStatement.execute();
            }
            connection.commit();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            try {
                createUserStatement.close();
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
