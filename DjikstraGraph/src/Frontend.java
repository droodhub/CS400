// --== CS400 File Header Information ==--
// Name: Ryan Follis
// Email: rfollis@wisc.edu
// Team: Red Team
// Group: HA
// TA: Hang Yin
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>

import java.util.Scanner;
import java.util.zip.DataFormatException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This is the main class driving the app, as it contains the main method that initializes
 * the app. User-generated input can be read in to perform one of several commands. The display
 * feature of the app displays five airports and their information to the console at once, and
 * it can be scrolled through.
 * @author ryanf
 *
 */
public class Frontend {
    
    private Scanner scnr = new Scanner(System.in);  // scanner for user input
    protected Backend flightApp = null;  // object created for backend method use
    private boolean done = false;  // only turns true once user exits the program

    /**
     * Main method initializes a frontend object that allows for reading user input.
     * @param args
     */
    public static void main(String[] args) {
        Frontend frontend = new Frontend();
        try {
            frontend.run();
        } catch (DataFormatException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Reads from the attached airports.csv file to gather information about all the individual
     * airports so paths and other information can be determined.
     * @throws DataFormatException
     */
    public void run() throws DataFormatException{
        try {
            flightApp = new Backend(new FileReader("airports.csv"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("*******************************************************************\n"
                         + "                     Welcome to CS400 Airlines!\n"
                         + "       To bring up the command menu, please press the 'M' key.\n"
                         + "*******************************************************************");
        
        while(!done) {
            String userInput = "";
            if(scnr.hasNext()) {
                userInput = scnr.next();
            }
            if(userInput.equalsIgnoreCase("M")) {
                menu();
            }
            if(userInput.equalsIgnoreCase("Shortest")) {
                shortest();
            }
            else if(userInput.equalsIgnoreCase("Price")) {
                price();
            }
            else if(userInput.equalsIgnoreCase("Options")) {
                System.out.println("There is only one shortest path between any given pair of airports.");
            }
            else if(userInput.equalsIgnoreCase("Display")) {
                display();
            }
            else if(userInput.equalsIgnoreCase("Total")) {
                System.out.println("The CS400 Airlines app is proudly partnered with "
                        + flightApp.allAirports.size() + "airports worldwide!");
            }
            else if(userInput.equalsIgnoreCase("Exit")) {
                done = true;  // causes the while loop to be exited and the message below to be output
            }
            else if(userInput.length() > 0) {
                System.out.println("Unrecognized command. Please use the 'M' key to view the "
                        + "list of available commands.");
            }
        }
        System.out.println("*******************************************************************\n"
                         + "                 Thank you for travelling with us!\n"
                         + "        We look forward to your next trip with CS400 Airlines!\n"
                         + "*******************************************************************");
    }
    
    /**
     * Displays the possible commands available to users of the app.
     */
    public void menu() {
        System.out.println("On the CS400 Airlines app, you may enter the following commands:");
        System.out.println("\"M\" - Default key to open the current menu");
        System.out.println("\"Shortest\" - Determines the shortest path between two selected airports");
        System.out.println("\"Price\" - Calculates the total price of a one-way trip between two selected airports");
        System.out.println("\"Options\" - Lists all options if there are multiple paths that are the same distance "
                + "between two given airports.");
        System.out.println("\"Display\" - Displays all airports partenered with our app for browsing purposes");
        System.out.println("\"Total\" - Displays the total number of airports partnered with our app");
        System.out.println("\"Exit\" - Closes the app");
    }
    
    /**
     * Lists all the airports needed to travel to within a shortest path between any pair of
     * airports provided by the user. The user enters two airports' IATA names into the scanner.
     */
    public void shortest() {
        Airport start = null;
        Airport destination = null;
        String startString = scnr.next();
        String destinationString = scnr.next();
        for(int i = 0; i < flightApp.allAirports.size(); i++) {
            if(startString.equalsIgnoreCase(flightApp.allAirports.get(i).getName())){
                start = flightApp.allAirports.get(i);
            }
            if(destinationString.equalsIgnoreCase(flightApp.allAirports.get(i).getName())){
                destination = flightApp.allAirports.get(i);
            }
        }
        if(start == null)
            System.out.println("The starting airport you searched for doesn't exist. You can "
                    + "return to the display screen to search for aiport names. Make sure you "
                    + "enter the official International Air Transport Association name.");
        if(start == null)
            System.out.println("The destination airport you searched for doesn't exist. You can "
                    + "return to the display screen to search for aiport names. Make sure you "
                    + "enter the official International Air Transport Association name.");
        if(start != null && destination != null) {
            ArrayList<Airport> shortest = flightApp.getShortestPath(start, destination);
            String statement = "The airports along the shortest path between the two provided "
                    + "airports is as follows: ";
            for(int i = 0; i < shortest.size(); i++) {
                statement += shortest.get(i).toString() + ";";
            }
            System.out.println(statement);
        }
    }
    
    /**
     * States the price of an entire trip between two provided airports. The price is based
     * solely on distance and grows at a linear rate as the total distance between the start
     * and destination airports increases. The user enters two airports' IATA names into the scanner.
     */
    public void price() {
        Airport start = null;
        Airport destination = null;
        String startString = scnr.next();
        String destinationString = scnr.next();
        for(int i = 0; i < flightApp.allAirports.size(); i++) {
            if(startString.equalsIgnoreCase(flightApp.allAirports.get(i).getName())){
                start = flightApp.allAirports.get(i);
            }
            if(destinationString.equalsIgnoreCase(flightApp.allAirports.get(i).getName())){
                destination = flightApp.allAirports.get(i);
            }
        }
        if(start == null)
            System.out.println("The starting airport you searched for doesn't exist. You can "
                    + "return to the display screen to search for aiport names. Make sure you "
                    + "enter the official International Air Transport Association name.");
        if(start == null)
            System.out.println("The destination airport you searched for doesn't exist. You can "
                    + "return to the display screen to search for aiport names. Make sure you "
                    + "enter the official International Air Transport Association name.");
        if(start != null && destination != null)
            System.out.println("The price of a flight between the two provided airports is: $"
                    + flightApp.getFlightCost(start, destination) + ".");
    }
    
    /**
     * Display mode shows five airports at a time, beginning from the top of the list of all
     * airports within the csv file. 
     */
    public void display() {
        System.out.println("Welcome to display mode. Press 'D' to scroll down the list, 'U'"
                + " to scroll back up, or 'X' to exit display mode.");
        int current = 0;
        boolean display = true;
        while(display) {
            for(int i = 0; i < 5; i++) {
                System.out.println(flightApp.allAirports.get(current + i).toString());
            }
            String displayInput = "";
            if(scnr.hasNext()) {
                displayInput = scnr.next();
            }
            if(displayInput.equalsIgnoreCase("D")) {
                if(current + 5 > flightApp.allAirports.size() - 5)
                    current = flightApp.allAirports.size() - 5;
                else {
                    current = current + 5;
                }
            }
            else if(displayInput.equalsIgnoreCase("U")) {
                if(current - 5 < 0)
                    current = 0;
                else {
                    current = current - 5;
                }
            }
            else if(displayInput.equalsIgnoreCase("X")) {
                display = false;
            }
            else if(displayInput.length() > 0) {
                System.out.println("Unrecognized command. Available commands in display mode are: "
                        + "'D' to scroll down, 'U' to scroll up, and 'X' to exit display mode.");
            }
        }
    }
    
}


