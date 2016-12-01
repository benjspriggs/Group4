package sqldb.dbo;

/**
 * Created by bspriggs on 11/30/2016.
 */
public class LocationTest extends DatabaseObjectTest {
    @Override
    protected DatabaseObject getImplementation() {
        return new Location(2, "street", "city", "state", "zipcode");
    }

    @Override
    protected Class getImplementationClass() {
        return Location.class;
    }
}