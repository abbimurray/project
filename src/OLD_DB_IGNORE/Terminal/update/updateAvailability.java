package OLD_DB_IGNORE.Terminal.update;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class updateAvailability {

    public static void main(String[] args) {
        final String DATABASE_URL = "jdbc:mysql://localhost:3306/EVCharging";

        // Create a Scanner object to read user input
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the chargerID of the entry you want to update:");
        int chargerID = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.println("Enter the startTime of the entry you want to update (YYYY-MM-DD HH:MM:SS):");
        String startTimeStr = scanner.nextLine();
        LocalDateTime startTime = LocalDateTime.parse(startTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); // Parse the input string to LocalDateTime with the specified pattern

        System.out.println("Enter the field you want to update (startTime, endTime, availability, chargerID):");
        String fieldToUpdate = scanner.nextLine();

        // Prompt the user to enter the new value for the chosen field
        System.out.println("Enter the new value for " + fieldToUpdate + ":");
        String newValue = scanner.nextLine();

        Connection connection = null;
        PreparedStatement pstat = null;
        int i = 0;

        try {
            // Establish connection to the database
            connection = DriverManager.getConnection(DATABASE_URL, "root", "pknv!47A");

            // Create prepared statement for updating the specified field in the table for the specified chargerID and startTime
            String updateQuery = "UPDATE availability SET " + fieldToUpdate + "=? WHERE chargerID=? AND startTime=?";
            pstat = connection.prepareStatement(updateQuery);
            pstat.setString(1, newValue);
            pstat.setInt(2, chargerID);
            pstat.setObject(3, startTime);

            // Update data in the table
            i = pstat.executeUpdate();
            System.out.println(i + " record(s) successfully updated in the database table.");

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                if (pstat != null) {
                    pstat.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        // Close the Scanner
        scanner.close();
    }
}