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

            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/chocan_server", "test", "password");
            Statement myStmt = conn.createStatement();

            ResultSet myRs = myStmt.executeQuery("SELECT * FROM member_info");

            while (myRs.next()) {
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

    private void generateData() {
        // make some data
        try {
            String insertStatement = "INSERT INTO providers " +
                    "(number, name) VALUES" +
                    "(?, ?)";
            PreparedStatement insert = conn.prepareStatement(insertStatement);
            for (int i = 0; i < 200; i++) {
                insert.setInt(1, i);
                insert.setString(2, "aslfkjasldfkjasdlfkajsf0");
                insert.executeUpdate();
            }
        } catch (Exception e) {
            // idk
        }
    }

    //returns the memberinfo for a specific member (Written by Michael Cohoe)
    public MemberInfo obtainMemberInfo(int id) {
        try {
            if (conn == null) {
                System.out.println("Connection has not been properly established");
                return null;
            }
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM " +
                    "member_info join locations_lookup on MEMBER_NUMBER = NUMBER join " +
                    "locations on locations_lookup.ID = locations.ID where " +
                    "member_info.number = " + id);

            ResultSet result = statement.executeQuery();
            if (result.next()) {

                String name = result.getString("name");
                String address = result.getString("street_address");
                String city = result.getString("city");
                String state = result.getString("state");
                String zip = result.getString("zipcode");
                MemberInfo info = new MemberInfo(name, address, city, state, zip);
                return info;
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //returns all serviceinfo for a specific member (Written by Michael Cohoe)
    public ArrayList<ServiceInfo> obtainMemServiceInfo(int id) {
        try {
            if (conn == null) {
                System.out.println("Connection has not been properly established");
                return null;
            }
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM " +
                    "performed_services join services_lookup on performed_services.SERVICE_ID = " +
                    "services_lookup.ID join service_info on " +
                    "service_info.SERVICE_CODE = services_lookup.SERVICE_CODE join " +
                    "providers on providers.NUMBER = services_lookup.PROVIDER_NUMBER join " +
                    "members on members.NUMBER = services_lookup.MEMBER_NUMBER where " +
                    "((select max(timestamp) from report_dates) < performed_services.timestamp " +
                    "or not exists (select * from report_dates))and members.NUMBER = " + id);

            ResultSet result = statement.executeQuery();
            ArrayList<ServiceInfo> array = new ArrayList<>();
            while (result.next()) {

                String date = result.getString("date_service");
                String prov_name = result.getString("providers.name");
                String service = result.getString("service_info.name");

                ServiceInfo info = new ServiceInfo(date, prov_name, service);
                array.add(info);
            }

            return array;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //returns providerinfo for a specific provider (written by Michael Cohoe)
    public ProviderInfo obtainProviderInfo(int id) {
        try {
            if (conn == null) {
                System.out.println("Connection has not been properly established");
                return null;
            }
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM " +
                    "providers join locations_lookup on providers.NUMBER = PROVIDER_NUMBER " +
                    "join locations on locations_lookup.location_id = locations.ID where " +
                    "providers.NUMBER = " + id);

            ResultSet result = statement.executeQuery();
            if (result.next()) {

                String name = result.getString("name");
                String address = result.getString("street_address");
                String city = result.getString("city");
                String state = result.getString("state");
                String zip = result.getString("zipcode");
                ProviderInfo info = new ProviderInfo(name, address, city, state, zip);
                return info;
            } else {
                return null;
            }

        } catch (SQLException e) {
            System.out.println("SQL problem in obtainProviderInfo");
        }
        return null;
    }

    //returns all serviceinfo for a specific provider (written by Michael Cohoe)
    public ArrayList<ServiceInfo> obtainProvServiceInfo(int id) {
        try {
            if (conn == null) {
                System.out.println("Connection has not been properly established");
                return null;
            }

            PreparedStatement statement = conn.prepareStatement("SELECT * FROM " +
                    "performed_services join services_lookup on performed_services.SERVICE_ID = " +
                    "services_lookup.ID join service_info on " +
                    "service_info.SERVICE_CODE = services_lookup.SERVICE_CODE join " +
                    "providers on providers.NUMBER = services_lookup.PROVIDER_NUMBER join " +
                    "members on members.NUMBER = services_lookup.MEMBER_NUMBER join " +
                    "member_info on members.NUMBER = member_info.NUMBER where " +
                    "((select max(timestamp) from report_dates) < performed_services.timestamp " +
                    "or not exists (select * from report_dates))and providers.NUMBER = " + id);

            ResultSet result = statement.executeQuery();
            ArrayList<ServiceInfo> array = new ArrayList<>();
            while (result.next()) {

                String date = result.getString("date_service");
                String timestamp = result.getString("timestamp");
                String prov_name = result.getString("providers.name");
                String service = result.getString("service_info.name");
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

    //returns an array of all provider names, their consultants, and each provider's total fee
    //(written by Michael Cohoe)
    public ArrayList<SummaryInfo> obtainSummaryInfo() {
        try {
            if (conn == null) {
                System.out.println("Connection has not been properly established");
                return null;
            }
            PreparedStatement statement = conn.prepareStatement("SELECT providers.name, " +
                    "count(*) AS consult_num, sum(service_info.fee) AS total_fee FROM " +
                    "services_lookup JOIN performed_services ON services_lookup.ID = " +
                    "performed_services.SERVICE_ID JOIN service_info ON service_info.SERVICE_CODE " +
                    "= services_lookup.SERVICE_CODE JOIN providers ON " +
                    "services_lookup.PROVIDER_NUMBER = providers.NUMBER WHERE ((SELECT max(timestamp) " +
                    "FROM report_dates) < performed_services.timestamp OR  NOT exists " +
                    "(SELECT * FROM report_dates)) GROUP BY providers.name");

            ResultSet result = statement.executeQuery();
            ArrayList<SummaryInfo> array = new ArrayList<>();

            while (result.next()) {

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

    //returns an array of all member ids (written by Michael Cohoe)
    public ArrayList<Integer> obtainMemberIDs() {
        try {
            if (conn == null) {
                System.out.println("Connection has not been properly established");
                return null;
            }
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM members");

            ResultSet result = statement.executeQuery();
            ArrayList<Integer> array = new ArrayList<>();

            while (result.next()) {
                array.add(result.getInt("number"));
            }
            return array;

        } catch (SQLException e) {
            System.out.println("SQL problem in obtainMemberID");
        }
        return null;
    }

    //returns an array of all provider ids (written by Michael Cohoe)
    public ArrayList<Integer> obtainProviderIDs() {
        try {
            if (conn == null) {
                System.out.println("Connection has not been properly established");
                return null;
            }
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM providers");

            ResultSet result = statement.executeQuery();
            ArrayList<Integer> array = new ArrayList<>();

            while (result.next()) {
                array.add(result.getInt("number"));
            }
            return array;

        } catch (SQLException e) {
            System.out.println("SQL problem in obtainProviderIDs");
        }
        return null;
    }

    //Adds a new timestamp to the file_write_dates table (written by Michael Cohoe)
    public void addFileWriteDate(Timestamp to_add) {
        try {
            if (conn == null) {
                System.out.println("Connection has not been properly established");
                return;
            }
            PreparedStatement statement = conn.prepareStatement("INSERT INTO report_dates " +
                    "(timestamp) VALUES (?)");
            statement.setTimestamp(1, to_add);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("SQL problem in addFileWriteDate");
        }

    }

    public int checkMemberValid(int memberID) {
        try {
            if (conn == null) {
                System.out.println("Connection has not been properly established");
                return -1;
            }
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM members " +
                    "where members.NUMBER =" + memberID);
            ResultSet result = statement.executeQuery();

            if (result.next()){
                if (result.getInt("IS_SUSPENDED") == 1) {
                    return 0;
                }
                else return 1;
            }



            return -1;

        } catch (SQLException e) {
            System.out.println("SQL problem in memberID");
            return -1;
        }
    }

    public ArrayList<ServiceInfo> obtainAllServices() {
        try {
            if (conn == null) {
                System.out.println("Connection has not been properly established");
                return null;
            }
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM " +
                    "service_info");

            ResultSet result = statement.executeQuery();
            ArrayList<ServiceInfo> array = new ArrayList<>();

            while (result.next()) {

                String name = result.getString("NAME");
                int code = result.getInt("SERVICE_CODE");


                ServiceInfo info = new ServiceInfo("", "", "", name, "",
                        code, 0, 0.0);

                array.add(info);
            }
            return array;

        } catch (SQLException e) {
            System.out.println("SQL problem in obtainProviderInfo");
        }
        return null;
    }

    public boolean checkServiceValid(int id) {
        try {
            if (conn == null) {
                System.out.println("Connection has not been properly established");
                return false;
            }
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM service_info " +
                    "where SERVICE_CODE =" + id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return true;
            }
            return false;

        } catch (SQLException e) {
            System.out.println("SQL problem in checkProviderValid");
            return false;
        }
    }


    public boolean checkProviderValid(int id)
    {
        try {
            if (conn == null){
                System.out.println("Connection has not been properly established");
                return false;
            }
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM providers " +
                    "where NUMBER =" + id);
            ResultSet result = statement.executeQuery();

            if(result.next()){
                return true;
            }
            return false;

        } catch (SQLException e) {
            System.out.println("SQL problem in checkProviderValid");
            return false;
        }
    }

    public int callToAdd(int id, int providerId, int serviceId, String comments, Date provided)
    {
        try {
            PreparedStatement statement = conn.prepareStatement("CALL create_performed_service" +
                    "( " + id + "," + providerId + "," + serviceId + "," + comments + "," + provided + ");");
            statement.executeUpdate();
            return 1;
        }catch(SQLException e) {
            System.out.println("SQL problem");
            return 0;
        }
    }


    //Create Member
    public int callCreateMember(int mem_id, int suspend, String name, String address, String city, String state, String zip){

        try {
            PreparedStatement statement =
                    conn.prepareStatement("CALL create_member(?,?,?,?,?,?,?)");
            statement.setInt(1,mem_id);
            statement.setInt(2,suspend);
            statement.setString(3,name);
            statement.setString(4,address);
            statement.setString(5,city);
            statement.setString(6,state);
            statement.setString(7,zip);
                    //    conn.prepareStatement("CALL create_member(" + mem_id + "," + suspend + "," + name + "," + address + ","
          //          + city + "," + state + "," + zip + ");");
            statement.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
        return 1;
    }


    //Edit member
    public int callEditMember(int memberID, int suspend, String name, String address, String city, String state, String zip){
        try{
            PreparedStatement statement = conn.prepareStatement("CALL update_member(?,?,?,?,?,?,?)");
            statement.setInt(1,memberID);
            statement.setInt(2,suspend);
            statement.setString(3,name);
            statement.setString(4,address);
            statement.setString(5,city);
            statement.setString(6,state);
            statement.setString(7,zip);
            //conn.prepareStatement("CALL" +
                 //   " update_member" + "( " + memberID + "," + suspend +"," + name + "," + address + ","
              //  + city + "," + state + "," + zip + ")");
            statement.executeUpdate();

        }catch(SQLException e){
            System.out.println("SQL problem");

        }
        return 1;
    }

    //Delete member
    public int callDeleteMember(int memberID){
        try{
            PreparedStatement statement =
                    conn.prepareStatement("DELETE FROM members WHERE number = " + memberID);
            statement.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }

        return 1;
    }

    //Create Provider
    public int callCreateProvider(int providerID, String name, String address, String city, String state, String zip) {
        try {
            PreparedStatement statement =
                    conn.prepareStatement("CALL create_provider(?,?,?,?,?,?,?)");
            statement.setInt(1,providerID);
            statement.setString(3,name);
            statement.setString(4,address);
            statement.setString(5,city);
            statement.setString(6,state);
            statement.setString(7,zip);
                    //conn.prepareStatement
                    // ("CALL create_provider" + "(" + providerID + "," + suspend+ "," + name + "," + address + "," + city + ","
                         //   + state + "," + zip + ")");
            statement.executeUpdate();
    }catch(SQLException e){
        System.out.println("SQL problem");
    }
        return 1;
    }


    //Edit Provider
    public int callEditProvider(int providerID, String name, String address, String city, String state, String zip){
        try{
            PreparedStatement statement =
                    conn.prepareStatement("CALL update_provider(?,?,?,?,?,?,?)");
            statement.setInt(1,providerID);
            statement.setString(3,name);
            statement.setString(4,address);
            statement.setString(5,city);
            statement.setString(6,state);
            statement.setString(7,zip);
                    //conn.prepareStatement("CALL update_provider" + "( " + memberID + "," + suspend +"," + name + "," + address + ","
                        //    + city + "," + state + "," + zip + ")");
            statement.executeUpdate();

        }catch(SQLException e){
            System.out.println("SQL problem");

        }

        return 1;
    }

    //Delete Provider
    public int callDeleteProvider(int providerID){
        try {
            PreparedStatement statement = conn.prepareStatement("DELETE FROM providers WHERE number = " + providerID);
            statement.executeUpdate();
        }catch(SQLException e){
            System.out.println("SQL problem");
        }
        return 1;
}

    public int obtainMaxMemberId() {
        try {
            if (conn == null) {
                System.out.println("Connection has not been properly established");
                return 0;
            }

            PreparedStatement statement = conn.prepareStatement("SELECT max(members.NUMBER) AS num FROM " +
                    "members");

            ResultSet result = statement.executeQuery();
            int numb = result.getInt("num") + 1;
            return numb;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public int obtainMaxProviderId() {
        try {
            if (conn == null) {
                System.out.println("Connection has not been properly established");
                return 0;
            }

            PreparedStatement statement = conn.prepareStatement("SELECT max(providers.NUMBER) as num FROM " +
                    "providers");

            ResultSet result = statement.executeQuery();
            return result.getInt("num") + 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


}
