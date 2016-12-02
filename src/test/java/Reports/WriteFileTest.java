package Reports;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Jeff on 12/1/16.
 */
public class WriteFileTest {
   private WriteFile writeFile;
    @Before
    public void setUp() throws Exception {
    writeFile = new WriteFile("MemberReports\\Member"+
            "Report_test" + ".txt");
    }

    @Test
    public void writeToFile() throws Exception {
        writeFile.writeToFile("isTestTrue");
    }

}