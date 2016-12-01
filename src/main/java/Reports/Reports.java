package Reports;

import sqldb.ChocanConnection;

import java.util.ArrayList;

/**
 * Created by The Borg on 11/25/2016.
 * aka Cameron
 * EDITIED TO COMPILE
 * TODO: MAKE SURE TO REMOVE REFERENCS TO UNIMPLEMENTED METHODS IN CHOCANCONNECTION
 */

public class Reports {
    private ChocanConnection conn;


    public Reports(ChocanConnection conn) {
        this.conn = conn;
    }

    //Prints a single member's report to the screen. Requires the id of the member
    //whose reports is to be viewed.
    public void PrintMemberReport(int memberID)
    {
        String to_print = WriteMemberReport(memberID);
        if (to_print == null) {
            System.out.println("Invalid member ID.");
        }
        else {
            System.out.println(to_print);
        }
    }

    //Prints a single provider's report to the screen. Requires the id of the provider
    //whose reports is to be viewed.
    public void PrintProviderReport(int providerID)
    {
        String to_print = WriteProviderReport(providerID);
        if (to_print == null) {
            System.out.println("Invalid provider ID.");
        }
        else {
            System.out.println(to_print);
        }
    }

    //Creates all member reports. Method prints out all member reports if the manager variable is
    //set to true. Otherwise it writes the reports to disk
    public void MemberSummaryReports(boolean isManager)
    {
        ArrayList<Integer> all_ids = conn.obtainMemberIDs();
        if (isManager && (all_ids == null || all_ids.size() == 0)){
            System.out.println("There are no member reports currently registered.");
        }

        if(all_ids != null) {

            for (Integer id : all_ids) {
                if (isManager) {
                    PrintMemberReport(id);
                } else {
                    WriteToDisk disk_writer = new WriteToDisk();
                    String to_write = WriteMemberReport(id);
                    if (to_write != null) {
                        disk_writer.WriteOutMember(to_write, id);
                    }
                }
            }
        }
    }

    //Creates all summary reports. Method prints out all summary reports if the manager variable is
    //set to true. Otherwise it writes the reports to disk
    public void ProviderSummaryReports(boolean isManager)
    {
        ArrayList<Integer> all_ids = conn.obtainProviderIDs();
        if (isManager && (all_ids == null || all_ids.size() == 0)){
            System.out.println("There are no provider reports currently registered.");
        }

        if(all_ids != null) {

            for (Integer id : all_ids) {
                if (isManager) {
                    PrintProviderReport(id);
                } else {
                    WriteToDisk disk_writer = new WriteToDisk();
                    String to_write = WriteProviderReport(id);
                    if (to_write != null) {
                        disk_writer.WriteOutProviders(to_write, id);
                    }
                }
            }
        }
    }

    //Creates the summary report. Method prints out the summary report if the manager variable is
    //set to true. Otherwise it writes the reports to disk
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
    public String WriteMemberReport(int id){


        if (conn == null){
            return null;
        }

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
        ArrayList<ServiceInfo> services = conn.obtainMemServiceInfo(id);

        int service_num = 1;

        //add services to report
        for(ServiceInfo service : services) {
            report += "Service " + Integer.toString(service_num) + "\n\t" + "Service Date: " +
                    service.getServe_date() + "\n\t" + "Provider Name: " + service.getProv_name() +
                    "\n\t" + "Service Name: " + service.getService() + "\n\n";
            service_num += 1;
        }
        if (service_num == 1){
            report += "No services were used this week\n\n";
        }

        return report;
    }

    //Takes in a provider ID and returns their report as a string
    public String WriteProviderReport (int id){

        if (conn == null){
            return null;
        }

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

        //obtain services info
        ArrayList<ServiceInfo> services = conn.obtainProvServiceInfo(id);
        int service_num = 1;

        //add services to report
        for(ServiceInfo service : services) {
            report += "Service " + Integer.toString(service_num) + "\n\t" + "Service Date: " +
                    service.getServe_date() + "\n\t" + "Date and Time Computer Received Data: " +
                    service.getTimestamp() + "\n\t" + "Member Name: " + service.getMem_name() +
                    "\n\t" + "Member Number: " + Integer.toString(service.getMem_id()) + "\n\t" +
                    "Service Code: " + Integer.toString(service.getService_id()) + "\n\t" +
                    "Fee: " + Double.toString(service.getFee()) + "\n\n";
            service_num += 1;
            total_consult +=1;
            total_fee += service.getFee();
        }

        if (service_num == 1){
            report += "No services were provided this week\n\n";
        }
        else {
            //append totals to report
            report += "Total Number of Consultants With Members: " +
                    Integer.toString(total_consult) + '\n' + "Total Fee: $" +
                    Double.toString(total_fee) + '\n';
        }

        return report;
    }

    //Returns a summary report as a stringe
    public String WriteSummaryReport (){

        int total_prov = 0;
        int total_consult = 0;
        double week_fee = 0.0;
        String report = "";

        if (conn == null){
            return null;
        }

        ArrayList<SummaryInfo> summaries = conn.obtainSummaryInfo();

        //add services to report
        if (summaries != null) {

            for (SummaryInfo summary : summaries) {
                total_prov += 1;
                report += "Provider " + Integer.toString(total_prov) + "\n\t" + "Provider Name: " +
                        summary.getProv_name() + "\n\t" + "Number of consultants for provider: " +
                        Integer.toString(summary.getConsult_num()) + "\n\t" +
                        "Total fee for provider: " + Double.toString(summary.getTotal_fee()) + "\n\n";
                total_consult += summary.getConsult_num();
                 week_fee += summary.getTotal_fee();
            }
        }


        week_fee = Math.round(week_fee * 100.0)  / 100.0;

        //add totals
        report += "Total amount of providers who provided a service: " +
                Integer.toString(total_prov) + '\n' + "Total amount of consultants: " +
                Integer.toString(total_consult) + '\n' + "Total fee for the week: " +
                Double.toString(week_fee) + '\n';


        return report;
    }
}
