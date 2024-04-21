package OLD_DB_IGNORE.Terminal.ADD;
/*works*/

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class addReservations {
    public static void main(String[] args) {
        final String DATABASE_URL = "jdbc:mysql://localhost:3306/EVCharging";
        Connection connection = null;
        PreparedStatement pstat = null;
        ResultSet resultSet = null;

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter reservation date and time (YYYY-MM-DD HH:MM:SS):");
        String reservationStr = scanner.nextLine();
        LocalDateTime reservation = LocalDateTime.parse(reservationStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        System.out.println("Enter status:");
        String status = scanner.nextLine();

        System.out.println("Enter userID");
        int userID = scanner.nextInt();

        System.out.println("Enter stationID");
        int stationID = scanner.nextInt();

        int i = 0;

        try {
            connection = DriverManager.getConnection(DATABASE_URL, "root", "pknv!47A");

            // Check if the userID exists in the users table
            pstat = connection.prepareStatement("SELECT * FROM user_accounts WHERE userID = ?");
            pstat.setInt(1, userID);
            resultSet = pstat.executeQuery();

            if (resultSet.next()) {
                // The userID exists, proceed with the reservation insertion
                // Check if the stationID exists in the charging_station table
                pstat = connection.prepareStatement("SELECT * FROM charging_station WHERE stationID = ?");
                pstat.setInt(1, stationID);
                resultSet = pstat.executeQuery();

                if (resultSet.next()) {
                    // The stationID exists, proceed with the reservation insertion
                    pstat = connection.prepareStatement("INSERT INTO reservations(reservationDateTime, status, userID, stationID) VALUES (?,?,?,?)");
                    pstat.setObject(1, reservation);
                    pstat.setString(2, status);
                    pstat.setInt(3, userID);
                    pstat.setInt(4, stationID);

                    i = pstat.executeUpdate();
                    System.out.println(i + " record(s) successfully added to the database (in the reservations table).");
                } else {
                    // The stationID does not exist, handle the error
                    System.out.println("Error: The specified stationID does not exist.");
                }
            } else {
                // The userID does not exist, handle the error
                System.out.println("Error: The specified userID does not exist.");
            }

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
    }
}