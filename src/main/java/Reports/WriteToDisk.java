package Reports;

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

    public void WriteOutSummary(String to_write)
    {
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
