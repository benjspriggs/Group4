package interactive.action;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;

/**
 * Created by bspriggs on 11/29/2016.
 */
public class WriteFileAction implements Action {
    private String body;
    private String path;

    public WriteFileAction(String path) {
        this.path = path;
    }

    public WriteFileAction(String body, String path) {
        this.body = body;
        this.path = path;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    public void writeLineToFile(String textLine) throws IOException {
        FileWriter write = new FileWriter(path, false);
        PrintWriter print_line = new PrintWriter(write);
        print_line.printf("%s" + "%n", textLine);
        print_line.close();
    }

    @Override
    public void execute() {
        try {
            FileUtils.writeStringToFile(new File(path), body, Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
