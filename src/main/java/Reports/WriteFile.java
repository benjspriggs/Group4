package Reports;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

/**
 * Created by Michael Cohoe on 11/26/2016.
 */

public class WriteFile {
    private String path;

    public WriteFile(String file_path) {
        path = file_path;
    }

    public void writeToFile(String textLine) throws IOException {
        FileWriter write = new FileWriter(path, false);
        PrintWriter print_line = new PrintWriter(write);
        print_line.printf("%s" + "%n", textLine);
        print_line.close();
    }
}
