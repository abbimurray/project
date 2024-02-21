package Create;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class add_Availability {

    public static void main(String[] args) {
        //database url
        final String DATABASE_URL = "jdbc:mysql://localhost:3306/EVCharging";
        Connection connection = null;
        PreparedStatement pstat = null;

        Scanner scanner = new Scanner(System.in);
        // inputs
        //inputs will be replaced - insert through gui
        System.out.println("Enter charger id of charger you wish to reserve:");
        int chargerID = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.println("Enter startTime (YYYY-MM-DD HH:MM:SS):");
        String startTimeStr = scanner.nextLine();
        LocalDateTime startTime = LocalDateTime.parse(startTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); // Parse the input string to LocalDateTime with the specified pattern

        System.out.println("Enter endTime (YYYY-MM-DD HH:MM:SS):");
        String endTimeStr = scanner.nextLine();
        LocalDateTime endTime = LocalDateTime.parse(endTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); // Parse the input string to LocalDateTime with the specified pattern


        System.out.println("Enter Availability status (available, in use, reserved):");
        String availability = scanner.nextLine();



        int i=0;


        try {
            //establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, "root", "pknv!47A");

            //create prepared statement for inserting into table
            pstat = connection.prepareStatement("INSERT INTO availability(startTime, endTime, availability, chargerID) VALUES (?,?,?,?)");
            pstat.setObject(1, startTime); // setObject to set LocalDateTime
            pstat.setObject(2, endTime);
            pstat.setString(3, availability);
            pstat.setInt(4,chargerID);


            //insert data into database
            i = pstat.executeUpdate();//if successful will return value of 1
            System.out.println(i + " :record successfully added to the database (in the availability table).");

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
