package ADD;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class addPayments {

    public static void main(String[] args) {
        final String DATABASE_URL = "jdbc:mysql://localhost:3306/EVCharging";
        Connection connection = null;
        PreparedStatement pstat = null;
        ResultSet resultSet = null;

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter userID");
        int userID = scanner.nextInt();

        System.out.println("Enter chargerID");
        int chargerID = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.println("Enter transactionID");
        int transactionID = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.println("Enter date and time (YYYY-MM-DD HH:MM:SS):");
        String dateStr = scanner.nextLine();
        LocalDateTime date = LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        System.out.println("Enter pay method:");
        String payMethod = scanner.nextLine();

        System.out.println("Enter amount");
        BigDecimal amount = scanner.nextBigDecimal();



        int i = 0;

        try {
            connection = DriverManager.getConnection(DATABASE_URL, "root", "pknv!47A");

            // Insert new payment details into the payments table
            String insertQuery = "INSERT INTO payments (transactionID, userID, chargerID, date, payMethod, amount) VALUES (?, ?, ?, ?, ?,?)";
            pstat = connection.prepareStatement(insertQuery);
            pstat.setInt(1, transactionID);
            pstat.setInt(2, userID);
            pstat.setInt(3, chargerID);
            pstat.setObject(4, date);
            pstat.setString(5, payMethod);
            pstat.setBigDecimal(6,amount);

            i = pstat.executeUpdate();
            System.out.println(i + " record(s) successfully added to the payments table.");

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