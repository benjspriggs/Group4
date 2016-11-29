package interactive.action;

import java.io.File;

/**
 * Created by bspriggs on 11/29/2016.
 */
abstract public class FileAction implements Action {
    private File file;
    private FileAction(){}
    FileAction(File f){
        file = f;
    }
    abstract public void execute();
}
