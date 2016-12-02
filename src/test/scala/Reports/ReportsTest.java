package Reports;

import org.junit.Before;
import org.junit.Test;
import sqldb.ChocanConnection;

import static org.junit.Assert.*;

/**
 * Created by Cameron on 12/1/2016.
 */
public class ReportsTest {
    private Reports reports;
    private ChocanConnection conn;

    @Before
    public void setUp() throws Exception {
        reports = new Reports(conn);
    }

    @Test
    public void printMemberReport() throws Exception {
        reports.PrintMemberReport(802774);
    }

    @Test
    public void printProviderReport() throws Exception {

    }

    @Test
    public void memberSummaryReports() throws Exception {

    }

    @Test
    public void providerSummaryReports() throws Exception {

    }

    @Test
    public void summarizeReports() throws Exception {

    }

    @Test
    public void writeMemberReport() throws Exception {

    }

    @Test
    public void writeProviderReport() throws Exception {

    }

    @Test
    public void writeSummaryReport() throws Exception {

    }

}