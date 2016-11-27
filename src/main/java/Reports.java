/**
 * Created by The Borg on 11/25/2016.
 * aka Cameron
 */
public class Reports {
    float serviceTotal;
    float ServicesFeesTotal;

    public void PrintMemberReport(int memberID)
    {

    }

    public void PrintProviderReport(int providerID)
    {

    }

    public void MemberSummaryReports(boolean isManager)
    {

    }

    public void ProviderSummaryReports(boolean isManager)
    {

    }

    public void SummerizeReports(boolean isManager)
    {

    }

    public String WriteMemberReport(int id){

        //Variables that will be written to the report. These will need to be updated to
        //pull info from the sql database.

        String name = "Michael";
        String address = "1234 street st";
        String city = "Portland";
        String state = "OR";
        int zip = 12345;

        //add member info to report
        String to_write = "Member Name: " + name + '\n' + "Member Number: " + Integer.toString(id) +
                '\n' + "Member Street Address: " + address + '\n' + "Member City: " + city + '\n' +
                "Member State: " + state + '\n' + "Member Zip Code: " + Integer.toString(zip)
                + "\n\n";

        //There will need to be some kind of loop here eventually to go through all services provided
        int service_num = 1;
        String serve_date = "01/02/16";
        String prov_name = "test name";
        String service = "test service";

        //append provider info to report
        to_write += "Service " + Integer.toString(service_num) + "\n\t" + "Service Date: " +
                serve_date + "\n\t" + "Provider Name: " + prov_name + "\n\t" + "Service Name: " +
                service + "\n\n";

        service_num = 2;
        serve_date = "01/03/16";
        prov_name = "test name2";
        service = "test service2";

        to_write += "Service " + Integer.toString(service_num) + "\n\t" + "Service Date: " +
                serve_date + "\n\t" + "Provider Name: " + prov_name + "\n\t" + "Service Name: " +
                service + '\n';

        return to_write;
    }

}
