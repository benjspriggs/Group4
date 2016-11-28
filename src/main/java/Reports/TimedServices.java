package Reports;

import sqldb.ChocanConnection;

/**
 * Created by The Borg on 11/25/2016.
 */
public class TimedServices {

    private ChocanConnection conn;

    public TimedServices(ChocanConnection conn){
        this.conn = conn;
    }
    
    public void callWeeklySummary()
    {

        Reports all_reports = new Reports(conn);
        all_reports.MemberSummaryReports(false);
        all_reports.ProviderSummaryReports(false);
        all_reports.SummarizeReports(false);
    }
}
