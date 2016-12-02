/**
 * Created by root on 11/28/16.
 */

//Search 'Wrapper goes here'

public class Menu extends Utilities{

    //Empty Constructor
    public Menu(){    }


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

        System.out.println("Welcome Healthcare Provider!!! Please choose from the following options =)");
        System.out.println("Create New Appointment (1)");
        System.out.println("View Service Directory (2)");
        System.out.println("Option: ");

        while (!input.hasNextInt() ){
            System.out.println("Option: ");
            input.next();
        }

        userInput = input.nextInt();

        if(userInput == 1)
            ; //Wrapper goes here
        else if(userInput == 2)
            ; //Wrapper goes here
        else
        {
            System.out.println("Seriously choose a valid input");
            providerMenu();
        }

    }


    private int addAppt()
    {
        int id = 0;
        boolean valid = false;
        int todaysDate;
        int serviceIdInput;

        System.out.println("Please enter a valid id number: ");

        id = input.nextInt();

        //validate the id num

        //If it's not valid return out
        if(!valid)
            return 0;

        //Gather the info yo



        return 1;
    }





    public void operatorMenu(){
        int userInput;

        System.out.println("Welcome ChocAn Operator!!! Please choose from the following options=D");
        System.out.println("Create new membership                  (1)");
        System.out.println("Edit existing membership               (2)");
        System.out.println("Delete existing membership             (3)");
        System.out.println("View appointments for the week         (4)");
        System.out.println("View summary off all provider reports  (5)");

        userInput = input.nextInt();

        if(userInput == 1)
            ; //Wrapper goes here
        else if(userInput == 2)
            ; //Wrapper goes here
        else if(userInput == 3)
            ; //Wrapper goes here
        else if(userInput == 4)
            ; //Wrapper goes here
        else if(userInput == 5)
            ; //Wrapper goes here
        else
        {
            System.out.println("Seriously choose a valid input");
            operatorMenu();
        }
    }


    public void managerMenu(){
        int userInput;

        System.out.println("Welcome Accounting Personell!!! Please choose from the following options =)");
        System.out.println("Suspend current member          (1)");
        System.out.println("Record fees for current member  (2)");
        System.out.println("View member summery reports      (3)");
        System.out.println("View appointment summery reports ()");


        userInput = input.nextInt();

        if(userInput == 1)
            ; //Wrapper goes here
        else if(userInput == 2)
            ; //Wrapper goes here
        else
        {
            System.out.println("Seriously choose a valid input");
            managerMenu();
        }

    }

}