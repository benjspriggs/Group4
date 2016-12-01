package interactive.action;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by bspriggs on 11/30/2016.
 */
abstract public class ReturnableActionTest<Result> {
    protected abstract ReturnableAction<Result> getReturnableActionImplementation();

    private ReturnableAction<Result> returnableAction;

    @Before
    public void setUp() throws Exception {
        returnableAction = getReturnableActionImplementation();
    }

    @Test
    public void executeAndReturn() throws Exception {
        assert(returnableAction.executeAndReturn() != null);
    }

}