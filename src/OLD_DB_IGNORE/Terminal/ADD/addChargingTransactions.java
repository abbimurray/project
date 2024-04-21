package OLD_DB_IGNORE.Terminal.ADD;
/*working*/

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class addChargingTransactions {
    public static void main(String[] args) {
        //database url
        final String DATABASE_URL = "jdbc:mysql://localhost:3306/EVCharging";
        Connection connection = null;
        PreparedStatement pstat = null;

        Scanner scanner = new Scanner(System.in);
        // inputs
        //inputs will be replaced - insert through gui
        //transaction id is auto incremented- pri key- int value
        System.out.println("Enter startTime (YYYY-MM-DD HH:MM:SS):");
        String startTimeStr = scanner.nextLine();
        LocalDateTime startTime = LocalDateTime.parse(startTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); // Parse the input string to LocalDateTime with the specified pattern

        System.out.println("Enter endTime (YYYY-MM-DD HH:MM:SS):");
        String endTimeStr = scanner.nextLine();
        LocalDateTime endTime = LocalDateTime.parse(endTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); // Parse the input string to LocalDateTime with the specified pattern

        System.out.println("Enter energy consumed:");
        BigDecimal energyConsumed = scanner.nextBigDecimal();
        scanner.nextLine(); // Consume the newline character

        System.out.println("Enter rate:");
        BigDecimal rate = scanner.nextBigDecimal();
        scanner.nextLine(); // Consume the newline character

        System.out.println("Enter total cost:");
        BigDecimal totalCost = scanner.nextBigDecimal();
        scanner.nextLine(); // Consume the newline character

        System.out.println("Enter user id:");
        int userID = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.println("Enter charger id:");
        int chargerID = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        int i=0;


        try {
            //establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, "root", "pknv!47A");

            //create prepared statement for inserting into table
            pstat = connection.prepareStatement("INSERT INTO chargingTransactions(startTime, endTime, energyConsumed,rate, totalCost, userID, chargerID) VALUES (?,?,?,?,?,?,?)");
            pstat.setObject(1, startTime); // setObject to set LocalDateTime
            pstat.setObject(2, endTime);
            pstat.setBigDecimal(3, energyConsumed);
            pstat.setBigDecimal(4, rate);
            pstat.setBigDecimal(5, totalCost);
            pstat.setInt(6, userID);
            pstat.setInt(7, chargerID);





            //insert data into database
            i = pstat.executeUpdate();//if successful will return value of 1
            System.out.println(i + " :record successfully added to the database (in the chargingTransactions table).");

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        try {
            pstat.close();
            connection.close();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
