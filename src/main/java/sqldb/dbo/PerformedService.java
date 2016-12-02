package sqldb.dbo;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

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
        return "INSERT INTO performed_services (service_id, date_service, timestamp, comments)" +
                "VALUES (?, ?, ?, ?);";
    }

    @Override
    public String show() {
        return "SELECT FROM performed_services (service_id, date_service, timestamp, comments)" +
                "WHERE service_id = ?;";
    }

    @Override
    public String update() {
        return "UPDATE performed_services SET" +
                "date_service = ?," +
                "timestamp = ?," +
                "comments = ?" +
                "WHERE service_id = ?;";
    }

    @Override
    public String delete() {
        return "DELETE FROM performed_services WHERE service_id = ?;";
    }

    @Override
    public void fillStatement(DatabaseAction action, PreparedStatement statement) throws SQLException {
        switch (action) {
            case SHOW:
            case DELETE:
                statement.setInt(1, service_id); break;
            case CREATE:
                statement.setInt(1, service_id);
                statement.setDate(2, date_service);
                statement.setTimestamp(3, timestamp);
                statement.setString(4, comments);
                break;
            case UPDATE:
                statement.setDate(1, date_service);
                statement.setTimestamp(2, timestamp);
                statement.setString(3, comments);
                statement.setInt(4, service_id);
                break;
        }
    }
}
