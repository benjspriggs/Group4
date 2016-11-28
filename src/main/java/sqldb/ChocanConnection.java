package sqldb;


import Reports.MemberInfo;
import Reports.ProviderInfo;
import Reports.ServiceInfo;
import Reports.SummaryInfo;

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
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM " +
                    "member_info where member_info.number = " + id);

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

    //method written by Michael Cohoe
    //returns all serviceinfo for a specific member
    //(CURRENTLY NOT TESTED)
    public ArrayList<ServiceInfo> obtainMemServiceInfo(int id){
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM " +
                    "services_lookup join service on service_code = service.code join " +
                    "performed_services_info on id = service_id join providers on " +
                    "providers.number = provider_info.number where member_number = " + id);

            ResultSet result = statement.executeQuery();
            ArrayList<ServiceInfo> array = new ArrayList<>();
            while(result.next()) {

                String date = result.getString("date_service");
                String prov_name = result.getString("providers.name");
                String service = result.getString("service.name");

                ServiceInfo info = new ServiceInfo(date, prov_name, service);
                array.add(info);
            }
            return array;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //method written by Michael Cohoe
    //returns providerinfo for a specific provider
    //(CURRENTLY NOT TESTED)
    public ProviderInfo obtainProviderInfo(int id) {
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM " +
                    "provider_info join providers on provider_info.number = providers.number where " +
                    "provider_info.number = " + id);

            ResultSet result = statement.executeQuery();
            if (result.next()) {

                String name = result.getString("name");
                String address = result.getString("street_address");
                String city = result.getString("city");
                String state = result.getString("state");
                String zip = result.getString("zipcode");
                ProviderInfo info = new ProviderInfo(name, address, city, state, zip);
                return info;
            }
            else{
                return null;
            }

        } catch (SQLException e) {
            System.out.println("SQL problem in obtainProviderInfo");
        }
        return null;
    }

    //method written by Michael Cohoe
    //returns all serviceinfo for a specific provider
    //(CURRENTLY NOT TESTED)
    public ArrayList<ServiceInfo> obtainProvServiceInfo(int id){
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM " +
                    "services_lookup join service on service_code = service.code join " +
                    "performed_services_info on id = service_id join providers on " +
                    "providers.number = provider_info.number join member_info on " +
                    "member_number = member_info.number where provider_number = " + id);

            ResultSet result = statement.executeQuery();
            ArrayList<ServiceInfo> array = new ArrayList<>();
            while(result.next()) {

                String date = result.getString("date_service");
                String timestamp = result.getString("timestamp");
                String prov_name = result.getString("providers.name");
                String service = result.getString("service.name");
                String mem_name = result.getString("member_info.name");
                int serve_id = result.getInt("service_id");
                int mem_id = result.getInt("member_number");
                double fee = result.getDouble("fee");

                ServiceInfo info = new ServiceInfo(date, timestamp, prov_name, service, mem_name,
                        serve_id, mem_id, fee);
                array.add(info);
            }
            return array;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //method written by Michael Cohoe
    //returns an array of all provider names, their consultants, and each provider's total fee
    //(CURRENTLY NOT TESTED)
    public ArrayList<SummaryInfo> obtainSummaryInfo() {
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT providers.name, count(*) as consult_num," +
                    " sum(service.fee) as total_fee FROM services_lookup join service on service_code = service.code " +
                    "join providers on provider_number = providers.number group by providers.name");

            ResultSet result = statement.executeQuery();
            ArrayList<SummaryInfo> array = new ArrayList<>();
            while(result.next()) {

                String name = result.getString("providers.name");
                int consults = result.getInt("consult_num");
                double fee = result.getDouble("total_fee");


                SummaryInfo info = new SummaryInfo(name, consults, fee);
                array.add(info);
            }
            return array;


        } catch (SQLException e) {
            System.out.println("SQL problem in obtainSummaryInfo");
        }
        return null;
    }

    //method written by Michael Cohoe
    //returns an array of all member ids
    //(CURRENTLY NOT TESTED)
    public ArrayList<Integer> obtainMemberIDs() {
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * from members");

            ResultSet result = statement.executeQuery();
            ArrayList<Integer> array = new ArrayList<>();

            while(result.next()) {
                array.add(result.getInt("number"));
            }
            return array;

        } catch (SQLException e) {
            System.out.println("SQL problem in obtainMemberID");
        }
        return null;
    }

    //method written by Michael Cohoe
    //returns an array of all provider ids
    //(CURRENTLY NOT TESTED)
    public ArrayList<Integer> obtainProviderIDs() {
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * from providers");

            ResultSet result = statement.executeQuery();
            ArrayList<Integer> array = new ArrayList<>();

            while(result.next()) {
                array.add(result.getInt("number"));
            }
            return array;

        } catch (SQLException e) {
            System.out.println("SQL problem in obtainProviderIDs");
        }
        return null;
    }
}



