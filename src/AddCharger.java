/*student name: abigail murray
 * student number: C00260073*/
/*CLASS FOR ADDING A charger*/
/*NB: HOW TO CONNECT CHARGER TO CHARGING STATION + transaction??
* tested with scanner - working*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class AddCharger{
    public static void main(String[] args) {
        //database url
        final String DATABASE_URL = "jdbc:mysql://localhost:3306/EVCharging";
        Connection connection = null;
        PreparedStatement pstat = null;
        Scanner scanner = new Scanner(System.in);
        // inputs
        //inputs will be replaced - insert through gui
        System.out.println("Enter ChargerID:");
        String chargerId = scanner.nextLine();

        System.out.println("Enter status:");
        String status = scanner.nextLine();

        System.out.println("Enter charger type:");
        String type = scanner.nextLine();

        System.out.println("Enter kw:");
        int  kw = scanner.nextInt();

        System.out.println("Enter cost per kwh:");
        double  costPerKwh = scanner.nextDouble();


        int i=0;


        try {
            //establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, "root", "pknv!47A");

            //create prepared statement for inserting into table
            pstat = connection.prepareStatement("INSERT INTO charger(ChargerID,Status,kW,CostPerKWh, type) VALUES (?,?,?,?,?)");
            pstat.setString(1, chargerId);
            pstat.setString(2, status);
            pstat.setInt(3, kw);
            pstat.setDouble(4, costPerKwh);
            pstat.setString(5,type);


            //insert data into database
            i = pstat.executeUpdate();//if successful will return value of 1
            System.out.println(i + " :record successfully added to the database");

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

