package ADD;
/*works*/
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class addChargers {
    // Database URL
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/EVCharging";

    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement pstat = null;

        try {
            // Establish connection to the database
            connection = DriverManager.getConnection(DATABASE_URL, "root", "pknv!47A");

            Scanner scanner = new Scanner(System.in);

            // Inputs
            System.out.println("Enter charger type:");
            String chargerType = scanner.nextLine();

            System.out.println("Enter stationId:");
            int stationId = scanner.nextInt();
            scanner.nextLine();  // Consume the newline character

            // Validate the stationId
            if (!isStationIdValid(connection, stationId)) {
                System.out.println("Error: The specified stationId does not exist.");
                return;  // Exit the program
            }

            System.out.println("Enter Status:");
            String status = scanner.nextLine();

            System.out.println("Enter kw :");
            int kw = scanner.nextInt();

            System.out.println("Enter cost per kwh :");
            BigDecimal costPerKWH = scanner.nextBigDecimal();

            // Create prepared statement for inserting into table
            pstat = connection.prepareStatement("INSERT INTO chargers (chargerType, stationID, status, kw, costPerKWH) VALUES (?,?,?,?,?)");
            pstat.setString(1, chargerType);
            pstat.setInt(2, stationId);
            pstat.setString(3, status);
            pstat.setInt(4, kw);
            pstat.setBigDecimal(5, costPerKWH);

            // Insert data into database
            int i = pstat.executeUpdate();  // If successful will return value of 1
            System.out.println(i + " :record successfully added to the database (in the chargers table).");

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
    }

    // Function to validate the stationId
    private static boolean isStationIdValid(Connection connection, int stationId) throws SQLException {
        // Prepare a statement to check if the stationId exists
        String query = "SELECT COUNT(*) AS count FROM charging_station WHERE stationID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, stationId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    return count > 0;  // If count > 0, the stationId exists
                }
            }
        }
        return false;  // Default to false if an error occurs
    }
}