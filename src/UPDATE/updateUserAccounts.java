package UPDATE;
/*works*/
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
public class updateUserAccounts {

    public static void main(String[] args) {
        final String DATABASE_URL = "jdbc:mysql://localhost:3306/EVCharging";

        // Create a Scanner object to read user input
        Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter the userID of the entry they want to update
        System.out.println("Enter the userID of the entry you want to update:");
        int userID = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        // Prompt the user to enter the field they want to update
        System.out.println("Enter the field you want to update (username, password, email, paymentMethod):");
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

            // Create prepared statement for updating the specified field in the table for the specified stationID
            String updateQuery = "UPDATE user_accounts SET " + fieldToUpdate + "=? WHERE userID=?";
            pstat = connection.prepareStatement(updateQuery);
            pstat.setString(1, newValue);
            pstat.setInt(2, userID);

            // Update data in the table
            i = pstat.executeUpdate();
            System.out.println(i + " record(s) successfully updated in the database table.");

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                pstat.close();
                connection.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        // Close the Scanner
        scanner.close();
    }

}