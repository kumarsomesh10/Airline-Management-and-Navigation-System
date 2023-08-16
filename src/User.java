/*
 * This class is intended to be the main class for this Project. All necessary methods are getting calls from this class.
 *
 *
 */

import java.util.ArrayList;
import java.util.List;

public class User {

    //        ************************************************************ Fields ************************************************************
    public static String input = "";
    public static boolean inputReady = false;
    /*2D Array to store admin credentials. Default credentials are stored on [0][0] index. Max num of admins can be 10....*/
    static String[][] adminUserNameAndPassword = new String[10][2];
    private static List<Customer> customersCollection = new ArrayList<>();
    public static String message = "";

    //        ************************************************************ Behaviours/Methods ************************************************************
    public static void addToMessage()
    {}
    public static void addToMessage(int n)
    {
        addToMessage(""+n);
    }
    public static void addToMessage(String s)
    {
        message += s+"\n";
    }

    public static String getStrInput(String txt)
    {
        input = "";
        inputReady = false;
        Textbox tb = new Textbox(txt);
        while (!inputReady)
        {
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        tb.dispose();
        inputReady = false;
        return input;
    }

    public static int getIntInput(String txt)
    {
        input = "";
        inputReady = false;
        Textbox tb = new Textbox(txt);
        while (!inputReady)
        {
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

        int temp = -1;
        try {
            temp = Integer.parseInt(input);
        } catch (Exception e) {
            temp = -1;
        }
        tb.dispose();
        inputReady = false;
        return temp;
    }

    public static void raiseError(String txt)
    {
        new ErrorPopup(txt);
    }

    static void showManual(String txt)
    {
        new Manual(txt);
    }

    public static void showFlights(String txt)
    {
        new FlightWindow(txt, 15);
    }

    public static void main(String[] args) {
        int countNumOfUsers = 1;
        RolesAndPermissions r1 = new RolesAndPermissions();
        Flight f1 = new Flight();
        FlightReservation bookingAndReserving = new FlightReservation();
        Customer c1 = new Customer();
        f1.flightScheduler();
        //Scanner read = new Scanner(System.in);


        int desiredOption = 0;
        


        do {
            //System.out.println("Welcome to loop");
            //Scanner read1 = new Scanner(System.in);
            /* If desiredOption is 1 then call the login method.... if default credentials are used then set the permission
             * level to standard/default where the user can just view the customer's data...if not found, then return -1, and if
             * data is found then show the user display menu for adding, updating, deleting and searching users/customers...
             * */
            displayMainMenu();
            desiredOption = getIntInput(User.message);
            //System.out.println("Desired option = "+desiredOption);
            while (desiredOption < 0 || desiredOption > 8) {
                displayMainMenu();
                raiseError("ERROR!!\nPlease enter value between 0 - 4. Enter the value again");
                desiredOption = getIntInput(User.message);
                //System.out.println("Desired option = "+desiredOption);
            }
            if(desiredOption == 0)
                break;

            if (desiredOption == 1) {

                /*Default username and password....*/
                adminUserNameAndPassword[0][0] = "root";
                adminUserNameAndPassword[0][1] = "root";
                
                addToMessage("\nEnter the UserName to login to the Management System :     ");
                String username = getStrInput(User.message);
                addToMessage("Enter the Password to login to the Management System :    ");
                String password = getStrInput(User.message);
                addToMessage();

                /*Checking the RolesAndPermissions......*/
                if (r1.isPrivilegedUserOrNot(username, password) == -1) {
                    raiseError("ERROR!!! Unable to login Cannot find user with the entered credentials.... Try Creating New Credentials or get yourself register by pressing 4....");
                } else if (r1.isPrivilegedUserOrNot(username, password) == 0) {
                    raiseError("You've standard/default privileges to access the data... You can just view customers data..." + "Can't perform any actions on them....");
                    c1.displayCustomersData(true);
                } else {
                    addToMessage("Logged in Successfully as "+username+"..... For further Proceedings, enter a value from below....");

                    /*Going to Display the CRUD operations to be performed by the privileged user.....Which includes Creating, Updating
                     * Reading(Searching) and deleting a customer....
                     * */
                    do {
                        addToMessage("Logged in as " + username);
                        addToMessage("(a) Enter 1 to add new Passenger....");
                        addToMessage("(b) Enter 2 to search a Passenger....");
                        addToMessage("(c) Enter 3 to update the Data of the Passenger....");
                        addToMessage("(d) Enter 4 to delete a Passenger....\n");
                        addToMessage("(e) Enter 5 to Display all Passengers....\n");
                        addToMessage("(f) Enter 6 to Display all flights registered by a Passenger...");
                        addToMessage("(g) Enter 7 to Display all registered Passengers in a Flight....");
                        addToMessage("(h) Enter 8 to Delete a Flight....");
                        addToMessage("(i) Enter 0 to Go back to the Main Menu/Logout....");
                        addToMessage("Enter the desired Choice :   ");
                        desiredOption = getIntInput(User.message);
                        /*If 1 is entered by the privileged user, then add a new customer......*/
                        if (desiredOption == 1) {
                            
                            c1.addNewCustomer();
                        } else if (desiredOption == 2) {
                            /*If 2 is entered by the privileged user, then call the search method of the Customer class*/
                            
                            c1.displayCustomersData(false);
                            addToMessage("Enter the CustomerID to Search :\t");
                            String customerID = getStrInput(User.message);
                            addToMessage();
                            c1.searchUser(customerID);
                        } else if (desiredOption == 3) {
                            /*If 3 is entered by the user, then call the update method of the Customer Class with required
                             * arguments.....
                             * */
                            
                            c1.displayCustomersData(false);
                            addToMessage("Enter the CustomerID to Update its Data :\t");
                            String customerID = getStrInput(User.message);
                            if (customersCollection.size() > 0) {
                                c1.editUserInfo(customerID);
                            } else {
                                raiseError("No Customer with the ID "+customerID+" Found...!!!\n");
                            }

                        } else if (desiredOption == 4) {
                            /*If 4 is entered, then ask the user to enter the customer id, and then delete
                             * that customer....
                             * */
                            
                            c1.displayCustomersData(false);
                            addToMessage("Enter the CustomerID to Delete its Data :\t");
                            String customerID = getStrInput(User.message);
                            if (customersCollection.size() > 0) {
                                c1.deleteUser(customerID);
                            } else {
                                raiseError("No Customer with the ID "+customerID+" Found...!!!\n");
                            }
                        } else if (desiredOption == 5) {
                            /*Call the Display Method of Customer Class....*/
                            
                            c1.displayCustomersData(false);
                        } else if (desiredOption == 6) {
                            
                            c1.displayCustomersData(false);
                            addToMessage("\n\nEnter the ID of the user to display all flights registered by that user...");
                            String id = getStrInput(User.message);
                            bookingAndReserving.displayFlightsRegisteredByOneUser(id);
                        } else if (desiredOption == 7) {
                            
                            addToMessage("Do you want to display Passengers of all flights or a specific flight.... 'Y/y' for displaying all flights and 'N/n' to look for a" +
                                    " specific flight.... ");
                            char choice = getStrInput(User.message).charAt(0);
                            if ('y' == choice || 'Y' == choice) {
                                bookingAndReserving.displayRegisteredUsersForAllFlight();
                            } else if ('n' == choice || 'N' == choice) {
                                f1.displayFlightSchedule();
                                addToMessage("Enter the Flight Number to display the list of passengers registered in that flight... ");
                                String flightNum = getStrInput(User.message);
                                bookingAndReserving.displayRegisteredUsersForASpecificFlight(flightNum);
                            } else {
                                addToMessage("Invalid Choice...No Response...!");
                            }
                        } else if (desiredOption == 8) {
                            
                            f1.displayFlightSchedule();
                            addToMessage("Enter the Flight Number to delete the flight : ");
                            String flightNum = getStrInput(User.message);
                            f1.deleteFlight(flightNum);

                        } else if (desiredOption == 0) {
                            
                            addToMessage("Thanks for Using BAV Airlines Ticketing System...!!!");

                        } else {
                            addToMessage("Invalid Choice...Looks like you're Robot...Entering values randomly...You've Have to login again...");
                            
                            desiredOption = 0;
                        }

                    } while (desiredOption != 0);

                }
            } else if (desiredOption == 2) {
                
                /*If desiredOption is 2, then call the registration method to register a user......*/
                addToMessage("\nEnter the UserName to Register :    ");
                String username = getStrInput(User.message);
                addToMessage("Enter the Password to Register :     ");
                String password = getStrInput(User.message);
                while (r1.isPrivilegedUserOrNot(username, password) != -1) {
                    raiseError("ERROR!!!\nAdmin with same UserName already exist.\nEnter new UserName");
                    username = getStrInput(User.message);
                    addToMessage("Enter the Password Again:   ");
                    password = getStrInput(User.message);
                }

                /*Setting the credentials entered by the user.....*/
                adminUserNameAndPassword[countNumOfUsers][0] = username;
                adminUserNameAndPassword[countNumOfUsers][1] = password;

                /*Incrementing the numOfUsers */
                countNumOfUsers++;
            } else if (desiredOption == 3) {
                
                addToMessage("\n\nEnter the Email to Login : \t");
                String userName = getStrInput(User.message);
                addToMessage("Enter the Password : \t");
                String password = getStrInput(User.message);
                String[] result = r1.isPassengerRegistered(userName, password).split("-");

                if (Integer.parseInt(result[0]) == 1) {
                    int desiredChoice;
                    addToMessage("\n\n%-20sLogged in Successfully as "+userName+"..... For further Proceedings, enter a value from below....");
                    do {
                        addToMessage("Logged in as " + userName);
                        addToMessage("(a) Enter 1 to Book a flight");
                        addToMessage("(b) Enter 2 to update your Data");
                        addToMessage("(c) Enter 3 to delete your account");
                        addToMessage("(d) Enter 4 to Display Flight Schedule");
                        addToMessage("(e) Enter 5 to Cancel a Flight");
                        addToMessage("(f) Enter 6 to Display all flights registered by "+userName);
                        addToMessage("(g) Enter 0 to Go back to the Main Menu/Logout");
                        addToMessage("(h) Enter 7 to input source and destination");
                        addToMessage("Enter the desired Choice :   ");
                        desiredChoice = getIntInput(User.message);
                        if (desiredChoice == 1) {
                            
                            f1.displayFlightSchedule();
                            addToMessage("\nEnter the desired flight number to book :\t ");
                            String flightToBeBooked = getStrInput(User.message);
                            addToMessage("Enter the Number of tickets for " + flightToBeBooked + " flight :   ");
                            int numOfTickets = getIntInput(User.message);
                            while (numOfTickets > 10) {
                                addToMessage("Enter the Number of tickets for " + flightToBeBooked + " flight :   ");
                                raiseError("ERROR!!\nYou can't book more than 10 tickets at a time for single flight\nEnter number of tickets again");
                                numOfTickets = getIntInput(User.message);
                            }
                            bookingAndReserving.bookFlight(flightToBeBooked, numOfTickets, result[1]);
                        } else if (desiredChoice == 2) {
                            
                            c1.editUserInfo(result[1]);
                        } else if (desiredChoice == 3) {
                            
                            addToMessage("Are you sure to delete your account...It's an irreversible action...Enter Y/y to confirm...");
                            char confirmationChar = getStrInput(User.message).charAt(0);
                            if (confirmationChar == 'Y' || confirmationChar == 'y') {
                                c1.deleteUser(result[1]);
                                addToMessage("User "+userName+"'s account deleted Successfully...!!!");
                                desiredChoice = 0;
                            } else {
                                addToMessage("Action has been cancelled...");
                            }
                        } else if (desiredChoice == 4) {
                            
                            f1.displayFlightSchedule();
                            f1.displayMeasurementInstructions();
                        } else if (desiredChoice == 5) {
                            
                            bookingAndReserving.cancelFlight(result[1]);
                        } else if (desiredChoice == 6) {
                            
                            bookingAndReserving.displayFlightsRegisteredByOneUser(result[1]);
                        }else if(desiredChoice==7){
                            f1.displayFlightSchedule();
                            addToMessage("\nEnter the source :\t ");
                            String src = getStrInput(User.message);
                            addToMessage("\nEnter destination :\t ");
                            String dst=getStrInput(User.message);
                            f1.dst_src(src, dst);
                        } 
                        else {
                            
                            if (desiredChoice != 0) {
                                addToMessage("Invalid Choice...Looks like you're Robot...Entering values randomly...You've Have to login again...");
                            }
                            desiredChoice = 0;
                        }
                    } while (desiredChoice != 0);

                } else {
                    raiseError("ERROR!!!\nUnable to login Cannot find user with the entered credentials....\nTry Creating New Credentials or get yourself register by pressing 4....");
                }
            } else if (desiredOption == 4) {
                
                c1.addNewCustomer();
            } else if (desiredOption == 5) {
                manualInstructions();
            }

            
        } while (true);
        
    }

    static void displayMainMenu() {
        addToMessage("(a) Press 0 to Exit.");
        addToMessage("(b) Press 1 to Login as admin.");
        addToMessage("(c) Press 2 to Register as admin.");
        addToMessage("(d) Press 3 to Login as Passenger.");
        addToMessage("(e) Press 4 to Register as Passenger.");
        addToMessage("(f) Press 5 to Display the User Manual.");
        addToMessage("Enter the desired option:    ");
    }

    static void manualInstructions() {
        addToMessage("(a) Press 1 to display Admin Manual.");
        addToMessage("(b) Press 2 to display User Manual.");
        addToMessage("Enter the desired option :    ");
        int choice = getIntInput(User.message);
        while (choice < 1 || choice > 2) {
            raiseError("ERROR!!!\nInvalid entry...Please enter a value either 1 or 2\nEnter again");
            addToMessage("(a) Press 1 to display Admin Manual.");
            addToMessage("(b) Press 2 to display User Manual.");
            addToMessage("Enter the desired option :    ");
            choice = getIntInput(User.message);
        }
        if (choice == 1) {
            addToMessage("(1) Admin have the access to all users data...Admin can delete, update, add and can perform search for any customer...");
            addToMessage("(2) In order to access the admin module, you've to get yourself register by pressing 2, when the main menu gets displayed...");
            addToMessage("(3) Provide the required details i.e., name, email, id...Once you've registered yourself, press 1 to login as an admin... ");
            addToMessage("(4) Once you've logged in, 2nd layer menu will be displayed on the screen...From here on, you can select from variety of options...");
            addToMessage("(5) Pressing \"1\" will add a new Passenger, provide the program with required details to add the passenger...");
            addToMessage("(6) Pressing \"2\" will search for any passenger, given the admin(you) provides the ID from the table printing above....  ");
            addToMessage("(7) Pressing \"3\" will let you update any passengers data given the user ID provided to program...");
            addToMessage("(8) Pressing \"4\" will let you delete any passenger given its ID provided...");
            addToMessage("(9) Pressing \"5\" will let you display all registered passenger...");
            addToMessage("(10) Pressing \"6\" will let you display all registered passengers...After selecting, program will ask, if you want to display passengers for all flights(Y/y) or a specific flight(N/n)");
            addToMessage("(11) Pressing \"7\" will let you delete any flight given its flight number provided...");
            addToMessage("(12) Pressing \"0\" will make you logged out of the program...You can login again any time you want during the program execution....");
            showManual(message);
        } else {
            addToMessage("(1) Local user has the access to its data only...He/She won't be able to change/update other users data...");
            addToMessage("(2) In order to access local users benefits, you've to get yourself register by pressing 4, when the main menu gets displayed...");
            addToMessage("(3) Provide the details asked by the program to add you to the users list...Once you've registered yourself, press \"3\" to login as a passenger...");
            addToMessage("(4) Once you've logged in, 3rd layer menu will be displayed...From here on, you embarked on the journey to fly with us...");
            addToMessage("(5) Pressing \"1\" will display available/scheduled list of flights...To get yourself booked for a flight, enter the flight number and number of tickets for the flight...Max num of tickets at a time is 10 ...");
            addToMessage("(7) Pressing \"2\" will let you update your own data...You won't be able to update other's data... ");
            addToMessage("(8) Pressing \"3\" will delete your account... ");
            addToMessage("(9) Pressing \"4\" will display randomly designed flight schedule for this runtime...");
            addToMessage("(10) Pressing \"5\" will let you cancel any flight registered by you...");
            addToMessage("(11) Pressing \"6\" will display all flights registered by you...");
            addToMessage("(12) Pressing \"0\" will make you logout of the program...You can login back at anytime with your credentials...for this particular run-time... ");
            showManual(message);
        }
    }

    
    //        ************************************************************ Setters & Getters ************************************************************

    public static List<Customer> getCustomersCollection() {
        return customersCollection;
    }
}