import Reports.Reports;
import Reports.WriteToDisk;
import sqldb.ChocanConnection;

/**
 * Created by bspriggs on 11/10/2016.
 */
public class Main {
    public static void main(String[] args) throws Exception{

        ChocanConnection obj = new ChocanConnection();
        System.out.println("Hello world!");

        Reports T = new Reports(obj);
        WriteToDisk m_report = new WriteToDisk();

        m_report.WriteOutMember(T.WriteMemberReport(123456789),123456789);
        m_report.WriteOutProviders(T.WriteProviderReport(987654321),987654321);
        T.SummarizeReports(false);
        T.PrintMemberReport(123456789);
        T.PrintProviderReport(987654321);
        T.SummarizeReports(true);



        obj.getProviderReport();

    }


}
