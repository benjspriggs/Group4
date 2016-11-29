package statement;

import java.io.File;
import java.sql.Connection;

/**
 * Created by bspriggs on 11/28/2016.
 */
public abstract class Statement {
    protected Statement() {}
    abstract void execute(Connection c);
    abstract void execute(File c);
}
