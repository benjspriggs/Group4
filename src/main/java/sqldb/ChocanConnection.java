package sqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by bspriggs on 11/15/2016.
 */
public class ChocanConnection {
    public static Connection getConnection() throws Exception {

        try {
            String driver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/datacenter";
            String username = "temp";
            String password = "password";

            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connected");
            return conn;

        } catch (Exception e) {
            System.out.println("Connected");
        }


        return null;

    }

    public static ArrayList<String> getProviderReport() throws Exception {
        try {
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM providers");

            ResultSet result = statement.executeQuery();

            ArrayList<String> array = new ArrayList<String>();
            while (result.next()) {
                System.out.print(result.getString("first"));
                System.out.print(" ");
                System.out.println("last");

                array.add(result.getString("last"));
            }

            System.out.println("DONE");
            return array;
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }
}
