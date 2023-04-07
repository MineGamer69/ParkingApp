import java.util.Scanner;
import java.io.*;

public class Garage{
    public boolean spotFilled[];
    public int numSpaces = 100;

    public Garage(){//Object that stores spaces and whether they are full.
        spotFilled = new boolean[numSpaces];
    }

    public void display(){//Prints a visual of the taken spots. 'X' means spot is taken.
        System.out.println("--------------------------------");
        for (int i=0; i<numSpaces; i++){
            if (spotFilled[i] == true){
                if((i+1)%10 == 0){
                    System.out.println("X  ");
                }
                else{
                    System.out.print("X  ");
                }
            }
            else{
                if((i+1)%10 == 0){
                    if((i+1)<10)
                        System.out.println("0"+(i+1) +" ");
                    else
                        System.out.println((i+1)+ " ");
                }
                else{
                    if((i+1)<10)
                        System.out.print("0"+(i+1) +" ");
                    else
                        System.out.print((i+1)+ " ");
                }
            }
        }
    }

    public void status(){//Prints the number of available spots and unavailable spots.
        int e = 0;
        int f = 0;
        for(int i = 0; i < numSpaces; i++){
            if(spotFilled[i] == false)
                e++;
            else
                f++;
        }
        if(e==1){
            System.out.println("--------------------------------");
            System.out.println("There is "+e+" available space.");
        }
        else{
            System.out.println("--------------------------------");
            System.out.println("There are "+e+" available spaces.");
        }
        if(f==1){
            System.out.println("There is "+f+" taken space.");
        }
        else{
            System.out.println("There are "+f+" taken spaces.");
        }
    }

    public void clearGarage(){//Sets all spots to empty.
        for(int i = 0; i < numSpaces; i++){
            spotFilled[i]=false;
        }
    }

    public void park(){//Park a car in a spot.
        Scanner in = new Scanner(System.in);
        boolean parked = false;
        while (parked == false){
            System.out.println("--------------------------------");
            System.out.print("Choose a spot number: ");
            int spot = in.nextInt();
            if (spot < 1 || spot > 100){
                System.out.print("Choose a valid spot number between 1 and 100: ");
            }
            else{
                if(spotFilled[spot-1]==true){
                    System.out.println("Spot "+spot+" is taken.");
                }
                else{
                    System.out.println("Parked in spot "+spot+".");
                    spotFilled[spot-1]=true;
                    parked = true;
                }
            }
        }
        in.close();
    }

    public void leave(){//Remove a car from a spot.
        Scanner in = new Scanner(System.in);
        boolean left = false;
        while(left == false){
            System.out.println("--------------------------------");
            System.out.print("Which spot is your car parked in? ");
            int spot = in.nextInt();
            if (spot < 1 || spot > 100){
                System.out.println("Choose a valid spot between 1 and 100");
            }
            else{
                if (spotFilled[spot-1]==false){
                    System.out.println("There is no car parked in spot "+spot+".");
                }
                else{
                    System.out.println("Leaving spot "+spot+".");
                    spotFilled[spot-1]=false;
                    left = true;
                }
            }
        }
        in.close();
    }

    public void save(){//Saves current status of garage to text file 'saveData.txt'.
        {
            PrintWriter output;
            FileWriter outfile;
            try
            {
                outfile = new FileWriter("savedData.txt");
                output =  new PrintWriter(outfile);
                
                for (int i=0; i<numSpaces; i++){
                    if (spotFilled[i] == true){
                        output.println("X");
                    }
                    else{
                        output.println("O");
                    }
                }
                output.close();
            }
            catch(IOException e)
            {
                System.out.println(e);
                System.exit(1);
            }
            System.out.println("--------------------------------");
            System.out.println("Saving.");
        }
    }

    public void load(){//Loads existing garage data from text file 'saveData.txt'.
		char inputLine;
		Scanner fileInput;
		File inFile = new File("savedData.txt");
        System.out.println("--------------------------------");
		System.out.println("Loading save data.");
        
		try 
		{
			fileInput = new Scanner(inFile);
			while(fileInput.hasNext())
			{
                for(int i = 0; i < 100; i++){
				    inputLine = fileInput.next().charAt(0);
                    if (inputLine == 'X'){
                        spotFilled[i]=true;
                    }
                    else if(inputLine == 'O'){
                        spotFilled[i]=false; 
                    }
			    } 			
		    }
        }
		catch (FileNotFoundException e)
		{System.out.println(e);
		System.exit(1);
		}
	}

    public void testEnterExit(){//Tests methods.
        load();
        status();
        display();
        park();
        park();
        display();
        leave();
        save();
    }
}
    
    
    
