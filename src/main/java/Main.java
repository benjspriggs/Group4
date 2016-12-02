import Reports.WriteToDisk;
import sqldb.ChocanConnection;

/**
 * Created by bspriggs on 11/10/2016.
 */
public class Main {
    public static void main(String[] args) throws Exception{

        ChocanConnection obj = new ChocanConnection();
        System.out.println("Hello world!");
        Menu menu = new Menu();

        menu.displayMenu();
    }


}
