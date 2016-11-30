package sqldb;

import Reports.MemberInfo;
import Reports.ProviderInfo;
import Reports.ServiceInfo;
import Reports.SummaryInfo;

import java.sql.*;
import java.util.ArrayList;
//import java.util.ArrayList;

public class ChocanConnection {
    private Connection conn;
    public ChocanConnection() {
        try {
            // get a connection to database

           conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/chocan_server", "root", "root");

           Statement myStmt = conn.createStatement();

           ResultSet myRs = myStmt.executeQuery("SELECT * from member_info");

           while (myRs.next()){
              System.out.println(myRs.getString("last"));
           }


            // create statement

            // execute query.
        } catch (Exception e) {
            // whatever
        }
    }


    private Connection getConnection() throws Exception {
        try {
            String driver = "org.apache.derby.jdbc.ClientDriver";
            String url = "jdbc:derby://localhost:1527/testdb";
            String username = "test";
            String password = "password1";

            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connected");
            return conn;



        } catch (Exception e) {
            System.out.println("Not connected, exception thrown");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }



        return null;

    }

    public ArrayList<String> getProviderReport() throws Exception {
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM providers");

            ResultSet result = statement.executeQuery();

            ArrayList<String> array = new ArrayList<>();
            while (result.next()) {
                System.out.print(result.getString("first"));
                System.out.print(" ");
                System.out.println("last");

                array.add(result.getString("last"));
            }

            System.out.println("DONE");
            return array;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    private void generateData(){
        // make some data
        try {
            String insertStatement ="INSERT INTO providers " +
                    "(number, name) VALUES" +
                    "(?, ?)";
            PreparedStatement insert = conn.prepareStatement(insertStatement);
            for (int i = 0; i < 200; i++){
                insert.setInt(1, i);
                insert.setString(2, "aslfkjasldfkjasdlfkajsf0");
                insert.executeUpdate();
            }
        } catch (Exception e){
            // idk
        }
    }

    public ArrayList<Integer> obtainMemberIDs() {
        return null;
    }

    public ArrayList<Integer> obtainProviderIDs() {
        return null;
    }

    public MemberInfo obtainMemberInfo(int id) {
        return null;
    }

    public ArrayList<ServiceInfo> obtainMemServiceInfo(int id) {
        return null;
    }

    public ProviderInfo obtainProviderInfo(int id) {
        return null;
    }

    public ArrayList<ServiceInfo> obtainProvServiceInfo(int id) {
        return null;
    }

    public ArrayList<SummaryInfo> obtainSummaryInfo() {
        return null;
    }
}

