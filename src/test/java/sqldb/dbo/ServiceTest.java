package sqldb.dbo;

import java.math.BigDecimal;

/**
 * Created by bspriggs on 12/1/2016.
 */
public class ServiceTest extends DatabaseObjectTest {
    @Override
    protected DatabaseObject getImplementation() {
        return new Service(0, "service_name",
                new BigDecimal(0.00), "description");
    }

    @Override
    protected Class getImplementationClass() {
        return Service.class;
    }
}