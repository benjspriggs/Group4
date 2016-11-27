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
        prov_name = "test name";
        service = "test service";

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

    }

    public void WriteOutSummary()
    {

    }
}
