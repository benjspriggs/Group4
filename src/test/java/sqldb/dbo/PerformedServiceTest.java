package sqldb.dbo;

import java.sql.Timestamp;
import java.sql.Date;

/**
 * Created by bspriggs on 12/1/2016.
 */
public class PerformedServiceTest extends DatabaseObjectTest {
    @Override
    protected DatabaseObject getImplementation() {
        return new PerformedService(0,
                new Date(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                "comments");
    }

    @Override
    protected Class getImplementationClass() {
        return PerformedService.class;
    }
}