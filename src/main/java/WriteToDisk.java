import java.io.IOException;

/**
 * Created by The Borg on 11/25/2016.
 */
public class WriteToDisk {
    public void WriteOutMember(String to_write, int id)
    {
        //write report to file
        WriteFile data = new WriteFile("MemberReport" + Integer.toString(id) + ".txt");
         try {
        data.writeToFile(to_write);

         }
         catch (IOException e){
             System.out.println("IOException caught");
         }
    }

    public void WriteOutProviders(String to_write, int id)
    {
        //write report to file
        WriteFile data = new WriteFile("ProviderReport" + Integer.toString(id) + ".txt");
        try {
            data.writeToFile(to_write);
        }
        catch (IOException e){
            System.out.println("IOException caught");
        }
    }

    public void WriteOutSummary()
    {
        //Variables that will be written to the report. These will need to be updated to
        //pull info from the sql database.

        int total_prov = 0;
        int total_consult = 0;
        double week_fee = 0;

        String prov_name = "test name";
        int consult_num = 2;
        double total_fee = 999.99;
        String to_write = "";

        total_prov += 1;
        total_consult += consult_num;
        week_fee += total_fee;

        to_write += "Provider " + total_prov + "\n\t" + "Provider Name: " + prov_name +
                "\n\t" + "Number of consultants for provider: " + Integer.toString(consult_num) +
                "\n\t" + "Total fee for provider: " + Double.toString(total_fee) + "\n\n";

         //Some sort of loop will be needed here to go over each provider.


        //add totals
        to_write += "Total amount of providers: " + Integer.toString(total_prov) + '\n' +
                "Total amount of consultants: " + Integer.toString(total_consult) + '\n' +
                "Total fee for the week: " + Double.toString(week_fee);

        //write report to file
        WriteFile data = new WriteFile("Accounts Payable.txt");
        try {
            data.writeToFile(to_write);
        }
        catch (IOException e){
            System.out.println("IOException caught");
        }


    }
}
