import java.util.Scanner;

public class Entrance
{
    public static int taken = 0;
    public static boolean park;

    public static void Enter() //Method for possible car entering garage.
    {
        System.out.println("Welcome to the Garage.");
        if(taken == 100)
        {
            System.out.println("We are sorry, the garage is at full capacity.");
        }
        else
        {
            System.out.println("There are " + (100 - taken) + " parking spaces open.");
            System.out.println("Would you like to park? \n1. Yes \n2. No");
            Scanner scan = new Scanner(System.in);
            int decision = scan.nextInt();
            
            switch(decision)
            {
                case 1:
                    park = true;
                    break;
                case 2:
                    park = false;
                    break;
                default:
                    System.out.println("Please try again.");
            }

            if(park == true)
            {
                System.out.println("You may enter.");
                taken++;
            }
            else
            {
                System.out.println("Please leave.");
            }
        } 
    }

    public static void Exit() //Method for possible car leaving garage.
    {
        System.out.println("Would you like to exit? \n1. Yes \n2. No");
        Scanner scan = new Scanner(System.in);
        int choice = scan.nextInt();
        if(choice == 1)
        {
            taken--;
            System.out.println("Have a nice day.");
        }
        else
        {
            System.out.println("Please return to your spot.");
        }
    }
}