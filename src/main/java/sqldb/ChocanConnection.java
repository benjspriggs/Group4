package sqldb;


import Reports.MemberInfo;

import java.sql.*;
import java.util.ArrayList;


/**
 * Created by bspriggs on 11/15/2016.
 */
public class ChocanConnection {
    private Connection conn;

    public ChocanConnection(){
        try {
            conn = getConnection();
            generateData();
        } catch (Exception e){
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

    //method written by Michael Cohoe
    //returns the memberinfo for a specific member
    //(CURRENTLY NOT TESTED)
    public MemberInfo obtainMemberInfo(int id) {
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM member_info where number = " + id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {

                String name = result.getString("name");
                String address = result.getString("street_address");
                String city = result.getString("city");
                String state = result.getString("state");
                String zip = result.getString("zipcode");
                MemberInfo info = new MemberInfo(name, address, city, state, zip);
                return info;
            }
            else{
                return null;
            }

        } catch (SQLException e) {
            System.out.println("SQL problem in obtainMemberInfo");
        }
        return null;
    }
}
