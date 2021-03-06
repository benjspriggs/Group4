package sqldb.dbo;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by bspriggs on 12/1/2016.
 */
public class Service extends DatabaseObject {
    private final int service_code;
    private final String name;
    private final BigDecimal fee;
    private final String description;

    public Service(int service_code, String name, BigDecimal fee, String description) {
        this.service_code = service_code;
        this.name = name;
        this.fee = fee;
        this.description = description;
    }

    @Override
    public String create() {
        return "INSERT INTO service_info (service_code, name, fee, description)" +
                "VALUES (?, ?, ?, ?);";
    }

    @Override
    public String show() {
        return "SELECT service_code, name, fee, description FROM service_info" +
                "WHERE service_code = ?;";
    }

    @Override
    public String update() {
        return "UPDATE service_info SET" +
                "name = ?," +
                "fee = ?," +
                "description = ?," +
                "WHERE service_code = ?;";
    }

    @Override
    public String delete() {
        return "DELETE FROM service_info WHERE service_code = ?;";
    }

    @Override
    public void fillStatement(DatabaseAction action, PreparedStatement statement) throws SQLException {
        switch (action) {
            case SHOW:
            case DELETE: statement.setInt(1, service_code); break;
            case CREATE:
                statement.setInt(1, service_code);
                statement.setString(2, name);
                statement.setBigDecimal(3, fee);
                statement.setString(4, description); break;
            case UPDATE:
                statement.setString(1, name);
                statement.setBigDecimal(2, fee);
                statement.setString(3, description);
                statement.setInt(4, service_code); break;
        }
    }
}
