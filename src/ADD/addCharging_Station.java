package ADD;
/*WORKS USING SCANNER TO GET INPUTS*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class addCharging_Station {
    public static void main(String[] args) {
        //database url
        final String DATABASE_URL = "jdbc:mysql://localhost:3306/EVCharging";
        Connection connection = null;
        PreparedStatement pstat = null;

        Scanner scanner = new Scanner(System.in);
        // inputs
        //inputs will be replaced - insert through gui
        //station id is auto incremented- pri key- int value
        System.out.println("Enter County:");
        String county = scanner.nextLine();

        System.out.println("Enter Address:");
        String address = scanner.nextLine();

        System.out.println("Enter  number of chargers:");
        int numChargers= scanner.nextInt();
        scanner.nextLine(); // Consume the newline character


        int i=0;


        try {
            //establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, "root", "pknv!47A");

            //create prepared statement for inserting into table
            pstat = connection.prepareStatement("INSERT INTO charging_stations(county, address, numberOfChargers) VALUES (?,?,?)");
            pstat.setString(1, county);
            pstat.setString(2, address);
            pstat.setInt(3, numChargers);



            //insert data into database
            i = pstat.executeUpdate();//if successful will return value of 1
            System.out.println(i + " :record successfully added to the database (in the charging_station table).");

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

