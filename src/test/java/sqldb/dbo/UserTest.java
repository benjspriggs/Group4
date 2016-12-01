package sqldb.dbo;

/**
 * Created by bspriggs on 11/30/2016.
 */
public class UserTest extends DatabaseObjectTest {
    @Override
    protected DatabaseObject getImplementation() {
        return new User(0, "username");
    }

    @Override
    protected Class getImplementationClass() {
        return User.class;
    }
}