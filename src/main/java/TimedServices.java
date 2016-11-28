/**
 * Created by The Borg on 11/25/2016.
 */
public class TimedServices {

    public void callWeeklySummary()
    {
        Reports all_reports = new Reports();
        all_reports.MemberSummaryReports(false);
        all_reports.ProviderSummaryReports(false);
        all_reports.SummerizeReports(false);
    }
}
