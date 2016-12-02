/**
 * Created by root on 11/28/16.
 */

import Reports.Reports;
import Reports.ServiceInfo;
import sqldb.ChocanConnection;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;


//Search 'Wrapper goes here'

public class Menu extends Utilities{
    enum UserType {
        Manager,
        Provider,
        Operator
    }

    int ID = 100000000;
    private ChocanConnection conn;
    private Reports report;

    public Menu(){
        conn = new ChocanConnection();
        report = new Reports(conn);
    }


    //Wrapper to call the correct menu type.  Takes an
    // int to determine what kind of user.
    //        1 == provider
    //        2 == operator
    //        3 == ChacAn account
    public void displayMenu(UserType userType){
        switch (userType) {
            case Manager: managerMenu(); break;
            case Provider: providerMenu(); break;
            case Operator: operatorMenu(); break;
        }
    }

    public void providerMenu()
    {
        int userInput;
        System.out.println("Please enter your ProviderID:");
        userInput = input.nextInt();
        if (conn.checkProviderValid(userInput)){
            System.out.println("Welcome Healthcare Provider!!! Please choose from the following options =)");
            HashMap<Integer, String> providerMenuOptions = fillProviderOptions();
            System.out.println("Option: ");

            while (!input.hasNextInt() ){
                System.out.println("Option: ");
                input.next();
            }

            userInput = input.nextInt();


            if(userInput == 1)
                printAllServices();
            else if(userInput == 2)
                verifyMember();
            else if(userInput == 3)
                createBilling();
            else {
                System.out.println("Seriously choose a valid input");
                providerMenu();
            }
        }

    }

    private HashMap<Integer,String> fillProviderOptions() {
        HashMap<Integer, String> h = new HashMap<>();
        h.put(1, "View Service Directory");
        h.put(2, "Check if member id is valid");
        h.put(3, "Create Billing Report for service provided");
        return h;
    }

    private HashMap<Integer,String> fillOperatorOptions() {
        HashMap<Integer, String> h = new HashMap<>();
        h.put(1, "Create new membership");
        h.put(2, "Edit existing membership");
        h.put(3, "Delete existing membership");
        h.put(4, "Create new provider");
        h.put(5, "Edit provider information");
        h.put(6, "Delete provider");
        return h;
    }

    private HashMap<Integer,String> fillManagerOptions() {
        HashMap<Integer, String> h = new HashMap<>();
        h.put(1, "Create new membership");
        h.put(2, "Edit existing membership");
        h.put(3, "Delete existing membership");
        h.put(4, "Create new provider");
        h.put(5, "Edit provider information");
        h.put(6, "Delete provider");
        h.put(7, "View summary of all reports");
        h.put(8, "View single member report");
        h.put(9, "View single provider report");
        h.put(10, "View all member reports");
        h.put(11, "View all provider reports");
        return h;
    }
    private int verifyMember(){
        boolean valid;
        int id;
        int userStat;

        System.out.println("Please enter a valid member id number: ");

        id = input.nextInt();
        //validate the id num
        //Returns ints
        //-1 ---> DNE
        //0  ---> Suspended
        //1  ---> Active

        userStat = conn.checkMemberValid(id); //On a different branch currently but it DOES exist.
        if(userStat == -1) {
            System.out.println("INVALID NUMBER");
            return -1;
        }
        else if(userStat == 0){
            System.out.println("SUSPENDED");
            return 0;
        }
        else if(userStat == 1){
            System.out.println("VALIDATED");
            return 1;
        }
        else
            return 2;
    }


    /******
     * STILL NEEDS TO SEND TO CREATE NEW BILLING
     *****/
    private int createBilling()
    {
        int id = 0;
        boolean valid = false;
        //int todaysDate;
        //int serviceIdInput;
        int year;
        int month;
        int day;
        int providerId;
        int serviceCode;
        String comments;

        //Verify.  if it's not valid gtfo
        if(verifyMember() != 1){
            System.out.println("The member is not valid for this!");
            return 0;}

        //Gather the info yo!!!

        //Get the current time stamp in a sql timestamp
        //java.sql.Timestamp current = new java.sql.Timestamp(System.currentTimeMillis());
        //Date service was provided
        System.out.print("Year: ");
        year = input.nextInt();
        System.out.println();
        System.out.print("Month: ");
        month = input.nextInt();
        System.out.println();
        System.out.print("Day: ");
        day = input.nextInt();
        System.out.println();

        Date providedDate = new Date(year, month, day); //int year, int month, int day
        System.out.print("");

        System.out.print("Please input provider number: ");
        providerId = input.nextInt();
        System.out.println();

        String quit;
        //Verify the Provider num.  Offer to try again or quit out.
        while(!conn.checkProviderValid(providerId))
        {
            System.out.println("That is not a valid provider number.");
            System.out.print("Try again or quit? (yes/quit) ");
            quit = input.next();
            if(quit == "quit")
                return 0;
            System.out.print("Please input provider number: ");
            providerId = input.nextInt();
            System.out.println();
        }

        //Prompt for service code list
        String yes;
        System.out.print("Do you need to see the list of service codes? (yes/no) ");
        yes = input.next();
        if(yes == "yes")
            printAllServices();

        //Gets the service code
        System.out.print("Please input the service code: ");
        serviceCode = input.nextInt();
        System.out.println();

        //Verify code is valid
        while(!conn.checkServiceValid(serviceCode))
        {
            System.out.println("That is not a valid service code.");
            System.out.print("Try again or quit? (yes/quit) ");
            quit = input.next();
            if(quit == "quit")
                return 0;
            System.out.print("Please input the service code: ");
            serviceCode = input.nextInt();
            System.out.println();
        }


        //Get the comments from them
        System.out.println("Comments: ");
        comments = input.next();


        //SEND TO CREATE NEW BILLING!!!

        //Wrapper in the conn piece.
        conn.callToAdd(id, providerId,  serviceCode, comments, providedDate);


/****
 PreparedStatement statement = conn.prepareStatement("CALL create_performed_service(id, " +
 "providerId, serviceCode, comments, providedDate");
 statement.executeQuery();

 statement conn.prepareStatement("")
 CALL create_performed_service(id, providerId, serviceCode, comments, providedDate);
 ********/
        return 1;
    }



/*
*  Print out all services
*        ---->list of service codes that are mapped to a service name.
*               -----> conn.obtainAllServices() returns array list of service info
*                       ----->This is an array of objects service info
*                       Loop thru the array to get the name and the code
 */

    private void printAllServices()
    {
        ArrayList<ServiceInfo> serviceCodes = conn.obtainAllServices();
        String to_print = "";
        for(ServiceInfo service : serviceCodes) {
            to_print += "Service Name " + service.getService() + "\n\t" + "Service Date: " +
                    service.getServe_date() + "\n\n";
        }
        if (to_print == ""){
            System.out.println("The service directory is empty");
        }
        else {
            System.out.println("The services are: \n");
            System.out.println(to_print);
        }
    }





    public void operatorMenu(){
        int userInput;

        System.out.println("Welcome ChocAn Operator!!! Please choose from the following options=D");
        HashMap<Integer, String> operatorMenuOptions = fillOperatorOptions();
        displayOptions(operatorMenuOptions);

        userInput = input.nextInt();

        switch (displayOptions(operatorMenuOptions)) {
            case 1: createMember(); break;
            case 2: editMember(); break;
            case 3: deleteMember(); break;
            case 4: createProvider(); break;
            case 5: editProvider(); break;
            case 6: deleteProvider(); break;
            default:
                System.out.println("Seriously choose a valid input");
                operatorMenu(); break;
        }
    }

    public void createMember(){
        String name;
        String address;
        String city;
        String state;
        String zip;
        int memberID;

        System.out.println("Please enter the member's first and last name:");
        name = input.next();

        System.out.println("Enter the member address:");
        address = input.next();

        System.out.println("Enter the city:");
        city = input.next();

        System.out.println("Enter the state abbreviation (for example Oregon is OR):");
        state = input.next();

        System.out.println("Enter the zip code:");
        zip = input.next();

        memberID = ID;
        ++ID;

        //conn.callCreateMember(memberID, false, name, address, city, state, zip);
    }

    public void editMember(){
        int memberID;
        String name;
        String address;
        String city;
        String state;
        String zip;

        System.out.println("Please enter the member ID of the member you'd like to update:");
        memberID = input.nextInt();

        System.out.println("Please enter the member's first and last name:");
        name = input.next();

        System.out.println("Enter the member address:");
        address = input.next();

        System.out.println("Enter the city:");
        city = input.next();

        System.out.println("Enter the state abbreviation (for example Oregon is OR):");
        state = input.next();

        System.out.println("Enter the zip code:");
        zip = input.next();

//
    }

    public void deleteMember(){
        int memberID;
        System.out.println("Please enter the member ID for the ChocAn member you would like to delete:");
        memberID = input.nextInt();

        //conn.callDeleteMember(memberID);
    }

    public void createProvider(){
        String name;
        String address;
        String city;
        String state;
        String zip;
        int providerID;

        System.out.println("Please enter the provider's first and last name:");
        name = input.next();

        System.out.println("Enter the provider address:");
        address = input.next();

        System.out.println("Enter the city:");
        city = input.next();

        System.out.println("Enter the state abbreviation (for example Oregon is OR):");
        state = input.next();

        System.out.println("Enter the zip code:");
        zip = input.next();

        providerID = ID;
        ++ID;

        //conn.callCreateProvider(providerID, false, name, address, city, state, zip);
    }

    public void editProvider(){
        String name;
        String address;
        String city;
        String state;
        String zip;
        int memberID;

        System.out.println("Please enter the provider ID for the provider you would like to update:");
        memberID = input.nextInt();

        System.out.println("Please enter the provider's first and last name:");
        name = input.next();

        System.out.println("Enter the member address:");
        address = input.next();

        System.out.println("Enter the city:");
        city = input.next();

        System.out.println("Enter the state abbreviation (for example Oregon is OR):");
        state = input.next();

        System.out.println("Enter the zip code:");
        zip = input.next();

        //conn.callEditProvider(memberID, false, name, address, city, state, zip);
    }

    public void deleteProvider(){
        int providerID;
        System.out.println("Please enter the provider ID for the ChocAn member you would like to delete:");
        providerID = input.nextInt();

        //conn.callDeleteProvider(providerID);
    }

    public void managerMenu(){
        int identifier;
        HashMap<Integer, String> managerMenuOptions = fillManagerOptions();
        System.out.println("Welcome ChocAn Manager!!! Please choose from the following options=D");
        switch (displayOptions(managerMenuOptions)) {
            case 1: createMember(); break;
            case 2: editMember(); break;
            case 3: deleteMember(); break;
            case 4: createProvider(); break;
            case 5: editProvider(); break;
            case 6: deleteProvider(); break;
            case 7: report.SummarizeReports(true);
            case 8:
                System.out.println();
                System.out.println("Please enter the member ID:");
                identifier = input.nextInt();
                report.PrintMemberReport(identifier);
                break;
            case 9:
                System.out.println("Please enter the provider ID:");
                identifier = input.nextInt();
                report.PrintProviderReport(identifier);
                break;
            case 10: report.MemberSummaryReports(true); break;
            case 11: report.ProviderSummaryReports(true); break;
            default:
                System.out.println("Seriously choose a valid input");
                operatorMenu();
                break;
        }
    }




}

