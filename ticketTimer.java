import java.util.Scanner;
import java.io.*;
import java.util.Date;

public class ticketTimer {
public long[] spaceTimes; // Array to store timer values for each spot


public ticketTimer(){ // Constructor for TicketTimer object
    spaceTimes = new long[100]; // Initialize the spaceTimes array with size 100
}

public void startTimer(int spot){ // Method to start timer for given spot
    Date date = new Date(); // Get current date and time
    long startTime = System.currentTimeMillis(); // Get current time in milliseconds
    spaceTimes[spot-1] = startTime; // Store the start time in the corresponding array element
    System.out.println("Timer started for car parked in spot "+spot+" on "+ date);
}

public boolean endTimer(int spot){// Method to end timer for given spot
    Date date = new Date(); // Get current date and time
    long endTime = System.currentTimeMillis(); // Get current time in milliseconds
    long timeElapsed = endTime - spaceTimes[spot-1]; // Calculate time elapsed by subtracting start time from end time
    System.out.println(timeElapsed);
    System.out.println("Timer ended for car parked in spot "+spot+" on "+ date +". "+ timeElapsed+" milliseconds passed.");
    spaceTimes[spot-1] = 0; // Reset the timer value for the spot to 0
    if (timeElapsed>30000) // Check if time elapsed is greater than 30000 milliseconds (30 seconds)
        return true; // Return true if time exceeded
    else
        return false; // Return false if time not exceeded
}

public void clearTimes(){ // Method to clear all saved timer values
    for(int i = 0; i < 100; i++){
        spaceTimes[i]=0; // Set all elements of spaceTimes array to 0
    }
}

public void loadTime(){ // Method to load timer values from timeData.txt
    long inputLine;
    Scanner fileInput;
    File inFile = new File("timeData.txt"); // Create a File object with the file name

    try 
    {
        fileInput = new Scanner(inFile); // Create a Scanner object to read from the file
        while(fileInput.hasNext())
        {
            for(int i = 0; i < 100; i++){
                inputLine = fileInput.nextLong(); // Read a long value from the file
                spaceTimes[i] = inputLine; // Store the value in the corresponding array element
            } 			
        }
    }
    catch (FileNotFoundException e)
    {
        System.out.println(e);
        System.exit(1);
    }
}

public void saveTime(){ // Method to save timer values to timeData.txt
    PrintWriter output;
    FileWriter outfile;
    try
    {
        outfile = new FileWriter("timeData.txt"); // Create a FileWriter object with the file name
        output =  new PrintWriter(outfile); // Create a PrintWriter object to write to the file

        for (int i=0; i<100; i++){
            output.println(spaceTimes[i]); // Write each timer value to a new line in the file
        }
        output.close(); // Close the PrintWriter
    }
    catch(IOException e)
    {
        System.out.println(e);
        System.exit(1);
        }
    }
}

