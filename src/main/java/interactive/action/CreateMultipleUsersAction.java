package interactive.action;

import java.sql.Connection;
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

    }
}
