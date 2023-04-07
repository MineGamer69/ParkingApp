import java.sql.*;
import java.util.Scanner;
import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.SQLException;

public class Ticket {

    private static final String DB_URL = "jdbc:sqlite:C:/Users/kapoo/Documents/CSC109/ParkingApp/tickets.db";
    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS tickets (id INTEGER PRIMARY KEY, user_id INTEGER, time_in DATETIME DEFAULT CURRENT_TIMESTAMP, fine INTEGER)";
    private static final String INSERT_TICKET_SQL = "INSERT INTO tickets (user_id, time_in, fine) VALUES (?, ?, ?)";
    private static final String SELECT_USER_TICKET_SQL = "SELECT id, fine FROM tickets WHERE user_id = ? AND time_in > datetime('now', '-1 day')";

    public static void main(String[] args) {
        try {
            // Open a connection to the database
            Connection conn = DriverManager.getConnection(DB_URL);

            // Create the tickets table if it doesn't already exist
            Statement createTableStmt = conn.createStatement();
            createTableStmt.executeUpdate(CREATE_TABLE_SQL);

            // Get the user ID from the console input and check if they already have a ticket
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter user ID: ");
            int userId = scanner.nextInt();
            PreparedStatement selectTicketStmt = conn.prepareStatement(SELECT_USER_TICKET_SQL);
            selectTicketStmt.setInt(1, userId);
            ResultSet selectTicketResult = selectTicketStmt.executeQuery();

            if (selectTicketResult.next()) {
                // User already has a ticket
                int ticketId = selectTicketResult.getInt("id");
                int fine = selectTicketResult.getInt("fine");
                System.out.println("User " + userId + " already has ticket " + ticketId + " with a fine of $" + fine);
            } else {
                // User does not have a ticket, so assign them one with a $10 fine
                PreparedStatement insertTicketStmt = conn.prepareStatement(INSERT_TICKET_SQL, Statement.RETURN_GENERATED_KEYS);
                insertTicketStmt.setInt(1, userId);
                insertTicketStmt.setString(2, "now");
                insertTicketStmt.setInt(3, 10);
                insertTicketStmt.executeUpdate();

                // Get the ID of the newly assigned ticket
                ResultSet insertTicketResult = insertTicketStmt.getGeneratedKeys();
                int ticketId = insertTicketResult.getInt(1);
                System.out.println("Assigned ticket " + ticketId + " to user " + userId + " with a fine of $10");
            }

            // Check if the user has overstayed their parking time
            PreparedStatement selectTicketStmt2 = conn.prepareStatement(SELECT_USER_TICKET_SQL);
            selectTicketStmt2.setInt(1, userId);
            ResultSet selectTicketResult2 = selectTicketStmt2.executeQuery();

            if (selectTicketResult2.next()) {
                int fine = selectTicketResult2.getInt("fine");

                System.out.println(selectTicketResult2);

                long hoursOverdue = (System.currentTimeMillis() - selectTicketResult2.getDate("time_in").getTime()) / (60 * 60 * 1000);

                if (hoursOverdue > 24) {
                    // Calculate the new fine based on how long the user has overstayed
                    int newFine = fine + ((int) (hoursOverdue - 24) * 10);

                    // Update the ticket with the new fine
                    PreparedStatement updateTicketStmt = conn.prepareStatement("UPDATE tickets SET fine = ? WHERE id = ?");
                    updateTicketStmt.setInt(1, newFine);
                    updateTicketStmt.setInt(2, selectTicketResult2.getInt("id"));
                    updateTicketStmt.executeUpdate();

                    System.out.println("User " + userId + " has overstayed their parking time and now has a fine of $" + newFine);
                }
            }

            // Close the database connection
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

