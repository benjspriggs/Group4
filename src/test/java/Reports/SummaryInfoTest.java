package Reports;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Jeff on 12/1/16.
 */
public class SummaryInfoTest {
    private SummaryInfo mySummaryInfoTest;
    @Before
    public void setUp() throws Exception {
        int consultNum = 0;
        double total_fee = 10;
        mySummaryInfoTest = new SummaryInfo("prov_name", consultNum, total_fee);
    }

    @Test
    public void getProv_name() throws Exception {
        assertNotNull("is null", mySummaryInfoTest.getProv_name());
    }

    @Test
    public void getConsult_num() throws Exception {
        assertNotNull("is null", mySummaryInfoTest.getConsult_num());
    }

    @Test
    public void getTotal_fee() throws Exception {
        assertNotNull("is null", mySummaryInfoTest.getTotal_fee());
    }

}