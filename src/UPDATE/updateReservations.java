package UPDATE;


import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class updateReservations {
    public static void main(String[] args) {
        final String DATABASE_URL = "jdbc:mysql://localhost:3306/EVCharging";
        Connection connection = null;
        PreparedStatement pstat = null;
        ResultSet resultSet = null;

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the Reservation ID of the entry you want to update (int value):");
        int reservationID = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        try {
            connection = DriverManager.getConnection(DATABASE_URL, "root", "pknv!47A");

            // Check if the reservationID exists in the reservations table
            pstat = connection.prepareStatement("SELECT * FROM reservations WHERE reservationID = ?");
            pstat.setInt(1, reservationID);
            resultSet = pstat.executeQuery();

            if (!resultSet.next()) {
                // The reservationID does not exist, handle the error and stop the program
                System.out.println("Error: The specified reservationID does not exist.");
                return; // Stop the program
            }

            System.out.println("Enter the field you want to update (reservationDateTime in format YYYY-MM-DD HH:MM:SS, status, userID, stationID):");
            String fieldToUpdate = scanner.nextLine();

            System.out.println("Enter the new value for " + fieldToUpdate + ":");
            String newValue = scanner.nextLine();

            // Rest of the code for updating the reservation based on the fieldToUpdate and newValue

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
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

        scanner.close();
    }
}

