package Reports;


import sqldb.ChocanConnection;

import java.util.ArrayList;

/**
 * Created by The Borg on 11/25/2016.
 * aka Cameron
 */

public class Reports {
    ChocanConnection conn;
    float serviceTotal;
    float ServicesFeesTotal;

    public Reports(ChocanConnection conn) {
        this.conn = conn;
    }

    //Prints a single member's report to the screen. Requires the id of the member
    //whose reports is to be viewed.
    public void PrintMemberReport(int memberID)
    {
        System.out.println(WriteMemberReport(memberID));
    }

    //Prints a single provider's report to the screen. Requires the id of the provider
    //whose reports is to be viewed.
    public void PrintProviderReport(int providerID)
    {
        System.out.println(WriteProviderReport(providerID));
    }

    //Creates all member reports. Method prints out all member reports if the manager variable is
    //set to true. Otherwise it writes the reports to disk
    public void MemberSummaryReports(boolean isManager)
    {

    }

    //Creates all summary reports. Method prints out all summary reports if the manager variable is
    //set to true. Otherwise it writes the reports to disk
    public void ProviderSummaryReports(boolean isManager)
    {

    }

    //Creates the summary report. Method prints out the summary report if the manager variable is
    //set to true. Otherwise it writes the report to disk
    public void SummarizeReports(boolean isManager)
    {
        if (isManager){
            System.out.println(WriteSummaryReport());
        }
        else{
            WriteToDisk disk_writer = new WriteToDisk();
            disk_writer.WriteOutSummary(WriteSummaryReport());
        }
    }

    //Takes in a member ID and returns their report as a string
    //CURRENTLY NOT TESTED
    public String WriteMemberReport(int id){

        //obtain member info
        MemberInfo mem_info =  conn.obtainMemberInfo(id);

        if (mem_info == null){
            return null;
        }

        //Write member info to report
        String report = "Member Name: " + mem_info.getName() + '\n' + "Member Number: " +
                Integer.toString(id) + '\n' + "Member Street Address: " + mem_info.getAddress() +
                '\n' + "Member City: " + mem_info.getCity() + '\n' + "Member State: " +
                mem_info.getState() + '\n' + "Member Zip Code: " + mem_info.getZip() + "\n\n";

        //obtain services info
        ArrayList<ServiceInfo> services = conn.obtainServiceInfo(id);
        int service_num = 1;

        //add services to report
        for(ServiceInfo service : services) {
            report += "Service " + Integer.toString(service_num) + "\n\t" + "Service Date: " +
                    service.getServe_date() + "\n\t" + "Provider Name: " + service.getProv_name() +
                    "\n\t" + "Service Name: " + service.getService() + "\n\n";
            service_num += 1;
        }

        return report;
    }

    //Takes in a provider ID and returns their report as a string
    public String WriteProviderReport (int id){

        //obtain provider info
        ProviderInfo prov_info =  conn.obtainProviderInfo(id);

        if (prov_info == null){
            return null;
        }

        //Write provider info to report
        String report = "Provider Name: " + prov_info.getName() + '\n' + "Provider Number: " +
                Integer.toString(id) + '\n' + "Provider Street Address: " + prov_info.getAddress() +
                '\n' + "Provider City: " + prov_info.getCity() + '\n' + "Provider State: " +
                prov_info.getState() + '\n' + "Provider Zip Code: " + prov_info.getZip() + "\n\n";

        double total_fee = 0;
        int total_consult = 0;





        //There will need to be some kind of loop here eventually to go through all services provided
        int service_num = 1;
        String serve_date = "01/02/16";
        String comp_time = "01/02/16 12:13:22";
        String mem_name = "Michael";
        int mem_num = 123456789;
        int serve_id = 123456;
        double fee = 799.99;



        //append service info to report
        report += "Service " + Integer.toString(service_num) + "\n\t" + "Service Date: " +
                serve_date + "\n\t" + "Date and Time Computer Received Data: " + comp_time +
                "\n\t" + "Member Name: " + mem_name + "\n\t" + "Member Number: " +
                Integer.toString(mem_num) + "\n\t" + "Service Code: " + Integer.toString(serve_id) +
                "\n\t" + "Fee: " + Double.toString(fee) + "\n\n";

        //second service for testing purposes. delete once sql access is created
        service_num = 2;
        serve_date = "01/03/16";
        comp_time = "01/02/16 11:12:21";
        mem_name = "Michael";
        mem_num = 123456789;
        serve_id = 654321;
        fee = 599.99;

        total_fee += fee;
        total_consult += 1;

        report += "Service " + Integer.toString(service_num) + "\n\t" + "Service Date: " +
                serve_date + "\n\t" + "Date and Time Computer Received Data: " + comp_time +
                "\n\t" + "Member Name: " + mem_name + "\n\t" + "Member Number: " +
                Integer.toString(mem_num) + "\n\t" + "Service Code: " + Integer.toString(serve_id) +
                "\n\t" + "Fee: $" + Double.toString(fee) + "\n\n";


        //append totals to report
        report += "Total Number of Consultants With Members: " +
                Integer.toString(total_consult) + '\n' + "Total Fee: $" +
                Double.toString(total_fee) + '\n';

        return report;
    }

    //Takes in a provider ID and returns their report as a string
    public String WriteSummaryReport (){
        //Variables that will be written to the report. These will need to be updated to
        //pull info from the sql database.

        int total_prov = 0;
        int total_consult = 0;
        double week_fee = 0;

        String prov_name = "test name";
        int consult_num = 2;
        double total_fee = 999.99;
        String report = "";

        total_prov += 1;
        total_consult += consult_num;
        week_fee += total_fee;

        report += "Provider " + total_prov + "\n\t" + "Provider Name: " + prov_name +
                "\n\t" + "Number of consultants for provider: " + Integer.toString(consult_num) +
                "\n\t" + "Total fee for provider: " + Double.toString(total_fee) + "\n\n";

        //Some sort of loop will be needed here to go over each provider.

        //add totals
        report += "Total amount of providers: " + Integer.toString(total_prov) + '\n' +
                "Total amount of consultants: " + Integer.toString(total_consult) + '\n' +
                "Total fee for the week: " + Double.toString(week_fee) + '\n';

        return report;
    }
}
