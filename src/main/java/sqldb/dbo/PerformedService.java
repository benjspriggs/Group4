package sqldb.dbo;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by bspriggs on 12/1/2016.
 */
public class PerformedService extends DatabaseObject {
    private final int service_id;
    private final Date date_service;
    private final Timestamp timestamp;
    private final String comments;

    public PerformedService(int service_id, Date date_service, Timestamp timestamp, String comments) {
        this.service_id = service_id;
        this.date_service = date_service;
        this.timestamp = timestamp;
        this.comments = comments;
    }

    @Override
    public String create() {
        return null;
    }

    @Override
    public String show() {
        return null;
    }

    @Override
    public String update() {
        return null;
    }

    @Override
    public String delete() {
        return null;
    }

    @Override
    public void fillStatement(DatabaseAction action, PreparedStatement statement) throws SQLException {

    }
}
