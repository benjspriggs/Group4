import java.io.IOException;

/**
 * Created by The Borg on 11/25/2016.
 */
public class WriteToDisk {
    public void WriteOutMember()
    {
        //Variables that will be written to the report. These will need to be updated to
        //pull info from the sql database.

        String name = "Michael";
        int id = 123456789;
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

        //write report to file
        WriteFile data = new WriteFile("MemberReport" + Integer.toString(id) + ".txt");
         try {
        data.writeToFile(to_write);

         }
         catch (IOException e){
             System.out.println("IOException caught");
         }

         //code will need to be written here to detect whether or not more
         //more member reports will need to be written

    }

    public void WriteOutProviders()
    {
        //Variables that will be written to the report. These will need to be updated to
        //pull info from the sql database.

        String name = "test name";
        int id = 987654321;
        String address = "4321 street st";
        String city = "Portland";
        String state = "OR";
        int zip = 54321;

        //add provider info to report
        String to_write = "Provider Name: " + name + '\n' + "Provider Number: " +
                Integer.toString(id) + '\n' + "Provider Street Address: " + address +
                '\n' + "Provider City: " + city + '\n' + "Provider State: " + state +
                '\n' + "Provider Zip Code: " + Integer.toString(zip) + "\n\n";

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

        total_fee += fee;
        total_consult += 1;

        //append provider info to report
        to_write += "Service " + Integer.toString(service_num) + "\n\t" + "Service Date: " +
                serve_date + "\n\t" + "Date and Time Computer Received Data: " + comp_time +
                "\n\t" + "Member Name: " + mem_name + "\n\t" + "Member Number: " +
                Integer.toString(mem_num) + "\n\t" + "Service Code: " + Integer.toString(serve_id) +
                "\n\t" + "Fee: " + Double.toString(fee) + "\n\n";

        service_num = 2;
        serve_date = "01/03/16";
        comp_time = "01/02/16 11:12:21";
        mem_name = "Michael";
        mem_num = 123456789;
        serve_id = 654321;
        fee = 599.99;

        total_fee += fee;
        total_consult += 1;

        to_write += "Service " + Integer.toString(service_num) + "\n\t" + "Service Date: " +
                serve_date + "\n\t" + "Date and Time Computer Received Data: " + comp_time +
                "\n\t" + "Member Name: " + mem_name + "\n\t" + "Member Number: " +
                Integer.toString(mem_num) + "\n\t" + "Service Code: " + Integer.toString(serve_id) +
                "\n\t" + "Fee: " + Double.toString(fee) + "\n\n";


        //Add totals and append to report
        to_write += "Total Number of Consultants With Members: " +
                Integer.toString(total_consult) + '\n' + "Total Fee: " +
                Double.toString(total_fee);

        //write report to file
        WriteFile data = new WriteFile("ProviderReport" + Integer.toString(id) + ".txt");
        try {
            data.writeToFile(to_write);

        }
        catch (IOException e){
            System.out.println("IOException caught");
        }

        //code will need to be written here to detect whether or not more
        //more member reports will need to be written
    }

    public void WriteOutSummary()
    {

    }
}
