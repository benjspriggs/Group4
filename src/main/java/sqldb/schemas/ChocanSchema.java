package sqldb.schemas;

import org.jetbrains.annotations.NotNull;
import sqldb.SqlDatabaseSchema;

/**
 * Created by bspriggs on 11/10/2016.
 */
public class ChocanSchema implements SqlDatabaseSchema {
    private final static int VERSION = 1;

    private final String MEMBER_SQL =
            "CREATE TABLE members ("+
            "number INT PRIMARY KEY NOT NULL,"+
            "is_suspended BOOLEAN NOT NULL"+
            ");";
    private final String MEMBER_INFO_SQL =
            "CREATE TABLE member_info ("+
            "number INT NOT NULL REFERENCES members(number),"+
            "name VARCHAR(140) NOT NULL,"+
            "street_address VARCHAR(50),"+
            "city VARCHAR(50),"+
            "state VARCHAR(2),"+
            "zipcode VARCHAR(32)"+
            ");";
    private final String PROVIDER_SQL =
            "CREATE TABLE providers ("+
            "number INT PRIMARY KEY NOT NULL,"+
            "name VARCHAR(140) NOT NULL"+
            ");";
    private final String PROVIDER_INFO_SQL =
            "CREATE TABLE provider_info ("+
            "number INT NOT NULL REFERENCES providers(number),"+
            "street_address VARCHAR(50),"+
            "city VARCHAR(50),"+
            "state VARCHAR(2),"+
            "zipcode VARCHAR(32)"+
            ");";
    private final String SERVICE_SQL =
            "CREATE TABLE service ("+
            "code INT NOT NULL,"+
            "name VARCHAR(140) NOT NULL,"+
            "fee DECIMAL(6,2) NOT NULL,"+
            "description VARCHAR(150)"+
            ");";
    private final String SERVICE_LOOKUP_SQL =
            "CREATE TABLE services_lookup ("+
            "service_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,"+
            "member_number INT NOT NULL REFERENCES members(number),"+
            "provider_number INT NOT NULL REFERENCES providers(number),"+
            "service_code INT NOT NULL REFERENCES service(code)"+
            ");";
    private final String PERFORMED_SERVICES_SQL =
            "CREATE TABLE performed_services_info ("+
            "id INT NOT NULL REFERENCES services_lookup(service_id),"+
            "date_service TIME NOT NULL,"+
            "timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,"+
            "comments VARCHAR(100)"+
            ");";
    private final String FILE_WRITE_DATES =
            "CREATE TABLE report_dates (" +
            "timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP);";

    private final String SQL = MEMBER_SQL + MEMBER_INFO_SQL
            + PROVIDER_SQL + PROVIDER_INFO_SQL
            + SERVICE_SQL + SERVICE_LOOKUP_SQL + PERFORMED_SERVICES_SQL
            + FILE_WRITE_DATES;

    @NotNull
    public String createTableStatement() {
        return SQL;
    }

    public int schemaVersion() {
        return VERSION;
    }

}
