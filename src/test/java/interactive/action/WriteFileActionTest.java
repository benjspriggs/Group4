package interactive.action;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Jeff on 12/1/16.
 */
public class WriteFileActionTest {
   private WriteFileAction writeFile;
   private String path;
    @Before
    public void setUp() throws Exception {
    writeFile = new WriteFileAction("Member"+
            "Report_test" + ".txt");
    }

    @Test
    public void writeToFile() throws Exception {
        writeFile.writeLineToFile("isTestTrue");
    }

}