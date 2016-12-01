package interactive.action;

/**
 * Created by bspriggs on 11/29/2016.
 */
public interface ReturnableAction<Result> extends Action {
    Result executeAndReturn();
}
