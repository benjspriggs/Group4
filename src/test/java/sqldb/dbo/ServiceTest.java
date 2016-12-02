package sqldb.dbo;

/**
 * Created by bspriggs on 12/1/2016.
 */
public class ServiceTest extends DatabaseObjectTest {
    @Override
    protected DatabaseObject getImplementation() {
        return new Service();
    }

    @Override
    protected Class getImplementationClass() {
        return Service.class;
    }
}