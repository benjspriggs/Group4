package Reports;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * Created by The Borg on 11/25/2016.
 */
public class WriteToDisk {
    public void WriteOutMember(String to_write, int id)
    {
        //write report to file
        LocalDate today = LocalDate.now( ZoneId.of("America/Los_Angeles"));
        WriteFile data = new WriteFile("Member"+ Integer.toString(id) +
                "Report" + today.toString() + ".txt");
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

        LocalDate today = LocalDate.now( ZoneId.of("America/Los_Angeles"));
        WriteFile data = new WriteFile("Provider"+ Integer.toString(id) +
                "Report" + today.toString() + ".txt");

        try {
            data.writeToFile(to_write);
        }
        catch (IOException e){
            System.out.println("IOException caught");
        }
    }

    public void WriteOutSummary(String to_write)
    {
        //write report to file
        LocalDate today = LocalDate.now( ZoneId.of("America/Los_Angeles"));
        WriteFile data = new WriteFile("Accounts Payable" + today.toString() + ".txt");
        try {
            data.writeToFile(to_write);
        }
        catch (IOException e){
            System.out.println("IOException caught");
        }
    }
}
