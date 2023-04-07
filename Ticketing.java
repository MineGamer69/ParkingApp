import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Ticketing {
    private static final String DB_URL = "jdbc:sqlite:C:/Users/kapoo/Documents/CSC109/ParkingApp/ticketing.db";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("What would you like to do?");
            System.out.println("1. Assign a ticket");
            System.out.println("2. View tickets");
            System.out.println("3. Quit");
            System.out.println("4. Clear ALL Tickets! (WARNING THIS IS PERMENANT!)");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    assignTicket(scanner);
                    break;
                case 2:
                    viewTickets();
                    break;
                case 3:
                    System.out.println("Exiting ticketing system...");
                    return;
                case 4:
                    System.out.println("DELETEING EVERYTING FOREVER!");
                    deleteAllTickets();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void assignTicket(Scanner scanner) {
        System.out.print("Enter student ID (limited to 7 digits): ");
        int studentId = scanner.nextInt();
        scanner.nextLine();

        if (String.valueOf(studentId).length() != 7) {
            System.out.println("Invalid student ID. Please enter a 7-digit number.");
            return;
        }

        LocalDateTime ticketTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            // Create the tickets table if it doesn't exist
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS tickets (student_id INTEGER, price INTEGER, ticket_time TEXT)");

            // Check if there is already a ticket for the student ID
            ResultSet rs = stmt.executeQuery("SELECT * FROM tickets WHERE student_id = " + studentId);

            if (rs.next()) {
                // There is already a ticket for this student ID, update it
                int totalPrice = rs.getInt("price") + 30;
                stmt.executeUpdate("UPDATE tickets SET price = " + totalPrice + " WHERE student_id = " + studentId);
                System.out.println("Added $30 to existing ticket for student ID " + studentId);
            } else {
                // There is no existing ticket for this student ID, create a new one
                String ticketTimeString = ticketTime.format(formatter);
                stmt.executeUpdate("INSERT INTO tickets VALUES (" + studentId + ", 30, '" + ticketTimeString + "')");
                System.out.println("Created new ticket for student ID " + studentId);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void viewTickets() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM tickets");
            System.out.println("Student ID\tPrice\t\tTicket Time");
            while (rs.next()) {
                int studentId = rs.getInt("student_id");
                int price = rs.getInt("price");
                String ticketTime = rs.getString("ticket_time");
                System.out.println(studentId + "\t\t$" + price + "\t\t" + ticketTime);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    private static void deleteAllTickets() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("DELETE FROM tickets");
                System.out.println("Deleted all tickets from the database.");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    
            
        
}
