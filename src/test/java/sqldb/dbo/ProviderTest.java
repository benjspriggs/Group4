package sqldb.dbo;

/**
 * Created by bspriggs on 11/30/2016.
 */
public class ProviderTest extends DatabaseObjectTest {
    @Override
    protected DatabaseObject getImplementation() {
        return new Provider(231, "provider name",
                new Location(1, "street", "city", "state", "zipcode"));
    }

    @Override
    protected Class getImplementationClass() {
        return Provider.class;
    }
}