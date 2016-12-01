package sqldb.dbo;

/**
 * Created by bspriggs on 11/30/2016.
 */
public class MemberTest extends DatabaseObjectTest {
    @Override
    protected DatabaseObject getImplementation() {
        return new Member(2, "name", true,
                new Location(2, "street", "city", "state", "zipcode"));
    }

    @Override
    protected Class getImplementationClass() {
        return Member.class;
    }
}