import java.util.Scanner;

public class ParkingApp {
    public static void main(String[]args){
        Scanner scan = new Scanner(System.in);
        boolean running = true;
        
        Garage a = new Garage();
        ticketTimer timer = new ticketTimer();
        
        System.out.println("Welcome to the Parking App");
        
        
        while(running == true){
            
            System.out.println("--------------------------------");
            System.out.println("What would you like to do?\n\n1) Park\n2) Leave\n3) Check Available Spaces\n4) Check Tickets\n5) Admin Controls\n0) Exit App\n");
            System.out.print("Enter choice: ");
            int choice = scan.nextInt();
            switch(choice){
                
                case 1:
                    //a.load();
                    timer.loadTime();
                    Entrance.Enter(a.taken());
                    a.park();
                    //timer.startTimer(a.chosenSpot());      
                    a.save();
                    //timer.saveTime();      
                    break;
                case 2:
                    a.load();
                    timer.loadTime();
                    Entrance.Exit();
                    if(timer.endTimer(a.leave())==true){
                        //Ticketing.assignTicket(scan);

                        //says method is not visible so I can't automatically create 
                        //a ticket outside of the Ticketing.startTicketing Method.
                        System.out.println("You have a new unpaid ticket.");//placeholder
                    }
                    else{

                    }
                    a.save();
                    timer.saveTime();
                    break;
                case 3:
                    a.load();
                    a.display();
                    a.status();
                    break;
                case 4:
                    Ticketing.ticketStart();
                    break;
                case 5:
                    System.out.println("--------------------------------");
                    System.out.println("Admin Controls\n\n1) Clear Garage\n2) Clear Timers\n0) Exit Admin Controls");
                    System.out.print("\nEnter choice: ");
                    choice = scan.nextInt();
                    switch(choice){
                        case 1:
                            a.clearGarage();
                            a.save();
                            continue;
                        case 2:
                            timer.clearTimes();
                            timer.saveTime();
                        case 0:
                            continue;
                    }
                case 0:
                    running = false;
                    


                

            }
        

        }


    }
}
