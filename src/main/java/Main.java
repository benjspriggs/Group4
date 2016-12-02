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

        Menu.UserType t = Menu.UserType.Provider;
        switch (args[0]){ // first argument is menu type
            case "manager": t = Menu.UserType.Manager; break;
            case "operator": t = Menu.UserType.Operator; break;
            default: break;
        }
        menu.displayMenu(t);
    }


}
