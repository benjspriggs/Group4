/**
 * Created by root on 11/28/16.
 */

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Utilities {
protected static FileReader fileReader;
    protected static Scanner input = null;

    public Utilities() {
        input = new Scanner(System.in);
    }

    public int displayOptions(HashMap<Integer, String> options) {
        for (Map.Entry<Integer, String> entry : options.entrySet()) {
            System.out.println(entry.getValue() + "\t\t\t" + entry.getKey());
        }
        System.out.println("Choose: ");
        while (!input.hasNextInt()) {
            System.out.println("Choose: ");
            input.next();
        }
        return input.nextInt();
    }
}
