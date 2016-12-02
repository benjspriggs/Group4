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
    private final int member_number;
    private final int provider_number;
    private final int service_code;
    private final Date date_service;
    private final Timestamp timestamp;
    private final String comments;

    public PerformedService(int service_id,
                            int member_number,
                            int provider_number,
                            int service_code,
                            Date date_service,
                            Timestamp timestamp,
                            String comments) {
        this.service_id = service_id;
        this.member_number = member_number;
        this.provider_number = provider_number;
        this.service_code = service_code;
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
            case UPDATE:
                break;
        }
    }
}
