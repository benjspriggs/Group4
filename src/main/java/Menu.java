/**
 * Created by root on 11/28/16.
 */

import Reports.ServiceInfo;
import sqldb.ChocanConnection;

import java.sql.Connection;
import sun.util.resources.cldr.ar.CalendarData_ar_LB;
import Reports.Reports;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.ArrayList;


//Search 'Wrapper goes here'

public class Menu extends Utilities{

    int ID = 100000000;
    private ChocanConnection conn;
    private Reports report;
    //Empty Constructor
    public Menu(){
        conn = new ChocanConnection();
        report = new Reports(conn);
    }


    //Wrapper to call the correct menu type.  Takes an
    // int to determine what kind of user.
    //        1 == provider
    //        2 == operator
    //        3 == ChacAn account
    public void displayMenu(int userType){
        if(userType == 1)
            providerMenu();
        else if(userType == 2)
            operatorMenu();
        else if(userType == 3)
            managerMenu();
    }

    public void providerMenu()
    {
        int userInput;
        System.out.println("Please enter your ProviderID:");
        userInput = input.nextInt();
        if (conn.checkProviderValid(userInput)){

            System.out.println("Welcome Healthcare Provider!!! Please choose from the following options =)");
            System.out.println("View Service Directory                     (1)");
            System.out.println("Check if member id is valid                (2)");
            System.out.println("Create Billing Report for service provided (3)");
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
        System.out.println("Create new membership                  (1)");
        System.out.println("Edit existing membership               (2)");
        System.out.println("Delete existing membership             (3)");
        System.out.println("Create new provider                    (4)");
        System.out.println("Edit provider information              (5)");
        System.out.println("Delete provider                        (6)");

        userInput = input.nextInt();

        if(userInput == 1)
            createMember();
        else if(userInput == 2)
            editMember();
        else if(userInput == 3)
            deleteMember();
        else if(userInput == 4)
            createProvider();
        else if(userInput == 5)
            editProvider();
        else if (userInput == 6)
            deleteProvider();
        else
        {
            System.out.println("Seriously choose a valid input");
            operatorMenu();
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

        conn.callCreateMember(memberID, false, name, address, city, state, zip);
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

        conn.callEditMember(memberID, false, name, address, city, state, zip);

    }

    public void deleteMember(){
        int memberID;
        System.out.println("Please enter the member ID for the ChocAn member you would like to delete:");
        memberID = input.nextInt();

        conn.callDeleteMember(memberID);
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

        conn.callCreateProvider(providerID, false, name, address, city, state, zip);
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

        conn.callEditProvider(memberID, false, name, address, city, state, zip);
    }

    public void deleteProvider(){
        int providerID;
        System.out.println("Please enter the provider ID for the ChocAn member you would like to delete:");
        memberID = input.nextInt();

        conn.callDeleteProvider(providerID);
    }

    public void managerMenu(){
        int userInput;
        int identifier;
        System.out.println("Welcome ChocAn Manager!!! Please choose from the following options=D");
        System.out.println("Create new membership                  (1)");
        System.out.println("Edit existing membership               (2)");
        System.out.println("Delete existing membership             (3)");
        System.out.println("Create new provider                    (4)");
        System.out.println("Edit provider information              (5)");
        System.out.println("Delete provider                        (6)");
        System.out.println("View summary of all reports            (7)");
        System.out.println("View single member report              (8)");
        System.out.println("View single provider report            (9)");
        System.out.println("View all member reports                (10)");
        System.out.println("View all provider reports              (11)");


        userInput = input.nextInt();

        if(userInput == 1)
            createMember();
        else if(userInput == 2)
            editMember();
        else if(userInput == 3)
            deleteMember();
        else if(userInput == 4)
            createProvider();
        else if(userInput == 5)
            editProvider();
        else if (userInput == 6)
            deleteProvider();
        else if (userInput == 7)
            report.SummarizeReports(true);
        else if (userInput == 8) {
            System.out.println("Please enter the member ID:");
            identifier = input.nextInt();
            report.PrintMemberReport(identifier);
        }
        else if (userInput == 9) {
            System.out.println("Please enter the provider ID:");
            identifier = input.nextInt();
            report.PrintProviderReport(identifier);
        }
        else if (userInput == 10)
            report.MemberSummaryReports(true);
        else if (userInput == 11)
            report.ProviderSummaryReports(true);
        else
        {
            System.out.println("Seriously choose a valid input");
            operatorMenu();
        }
    }


    }

}