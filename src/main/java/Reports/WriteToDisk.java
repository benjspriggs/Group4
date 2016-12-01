package Reports;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * Created by The Borg on 11/25/2016.
 */
public class WriteToDisk {
    public void WriteOutMember(String to_write, int id)
    {
        //Create MemberReports directory if needed
        Path path = Paths.get("MemberReports");
        //if directory exists?
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                //fail to create directory
                e.printStackTrace();
            }
        }

        //write report to file
        LocalDate today = LocalDate.now( ZoneId.of("America/Los_Angeles"));
        WriteFile data = new WriteFile("MemberReports\\Member"+ Integer.toString(id) +
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

        //Create ProviderReports directory if needed
        Path path = Paths.get("ProviderReports");
        //if directory exists?
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                //fail to create directory
                e.printStackTrace();
            }
        }

        //write report to file
        LocalDate today = LocalDate.now( ZoneId.of("America/Los_Angeles"));
        WriteFile data = new WriteFile("ProviderReports\\Provider"+ Integer.toString(id) +
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
        //Create SummaryReports directory if needed
        Path path = Paths.get("SummaryReports");
        //if directory exists?
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                //fail to create directory
                e.printStackTrace();
            }
        }

        //write report to file
        LocalDate today = LocalDate.now( ZoneId.of("America/Los_Angeles"));
        WriteFile data = new WriteFile("SummaryReports\\Accounts Payable" + today.toString() + ".txt");
        try {
            data.writeToFile(to_write);
        }
        catch (IOException e){
            System.out.println("IOException caught");
        }
    }
}
