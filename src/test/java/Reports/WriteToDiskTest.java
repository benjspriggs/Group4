package Reports;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Jeff on 12/1/16.
 */
public class WriteToDiskTest {
    private WriteToDisk myWriteDisk;
    @Before
    public void setUp() throws Exception {
        myWriteDisk = new WriteToDisk();

    }

    @Test
    public void writeOutMember() throws Exception {
        myWriteDisk.WriteOutMember("writeout_member.txt", 12345);
    }

    @Test
    public void writeOutProviders() throws Exception {
        myWriteDisk.WriteOutProviders("writeout_provider.txt", 12345);
    }

    @Test
    public void writeOutSummary() throws Exception {
        myWriteDisk.WriteOutSummary("writeout_summary.txt");
    }

}