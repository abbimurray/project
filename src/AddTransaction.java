/*student name: abigail murray
 * student number: C00260073*/
/*FOR ADDING transaction DETAILS*/
/*nb: connecting account id and chargerid?*/
/*total cost method*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date; /*need this for date */
import java.sql.Timestamp;
import java.sql.Time;
import java.math.BigDecimal;
import java.util.Scanner;

public class AddTransaction {
    public static void main(String[] args) {
        //database url
        final String DATABASE_URL = "jdbc:mysql://localhost:3306/EVCharging";
        Connection connection = null;
        PreparedStatement pstat = null;
        Scanner scanner = new Scanner(System.in);
        // inputs
        //inputs will be replaced - insert through gui
        System.out.println("Enter account id:");
        int accountid = scanner.nextInt();
        System.out.println("Enter date of transaction:");
        Date dateOfTransaction = Date.valueOf(scanner.nextLine());
        System.out.println("Enter start time of transaction:");
        Timestamp startTime = Timestamp.valueOf(scanner.nextLine());
        System.out.println("Enter end time of transaction:");
        Timestamp endTime=  Timestamp.valueOf(scanner.nextLine());
        // Calculate the duration
        long durationMs = endTime.getTime() - startTime.getTime();
        Time duration = new Time(durationMs);
        System.out.println("Duration: " + duration);

        //calculate usage
        double powerConsumptionRate = 5.0;// set
        // Calculate the duration in hours
        double durationHours = (double) durationMs / (1000 * 60 * 60); // Convert duration from milliseconds to hours
        // Calculate the used kilowatt-hours
        BigDecimal usedkW = BigDecimal.valueOf(durationHours * powerConsumptionRate);
        System.out.println("Used kWh: " + usedkW);

        // calculate total cost
        System.out.println("Enter cost per Kwh:");
        BigDecimal costPerKwh= BigDecimal.valueOf(scanner.nextDouble());

        BigDecimal totalCost = usedkW.multiply(costPerKwh);
        System.out.println("Total Cost: " + totalCost);







       /* Date dateOfTransaction = Date.valueOf("2024-02-06");// = Date.valueOf("2027-06-01"); // Use Date.valueOf to convert a string to a SQL date
        Timestamp startTime = Timestamp.valueOf("2024-02-06 09:00:00");
        Timestamp endTime = Timestamp.valueOf("2024-02-06 09:32:00");
        // Calculate the duration in milliseconds
        long durationInMillis = endTime.getTime() - startTime.getTime();
        Time duration = new Time(durationInMillis);// Create a Time object representing the duration
        BigDecimal usedkW = new BigDecimal("15.946");// Create a BigDecimal object with the decimal value
        BigDecimal costPerKwh = new BigDecimal("0.682");
        //calculate total cost
        long totalSeconds = duration.getTime() / 1000; // Convert the Time duration to the total number of seconds
        BigDecimal totalCost = costPerKwh.multiply(new BigDecimal(totalSeconds)); //calculate total cost
*/

//pstat.setBigDecimal(9, decimalValue); // Use setBigDecimal to set the decimal value in the prepared statement*/


        int i=0;


        try {
            //establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, "root", "pknv!47A");

            //create prepared statement for inserting into table
            pstat = connection.prepareStatement("INSERT INTO transaction (Date,StartTime,Endtime, usedkW,CostPerKWH, duration,TotalCost, accountid) VALUES (?,?,?,?,?,?,?,?)");
            pstat.setDate(1, dateOfTransaction);
            pstat.setTimestamp(2,startTime);
            pstat.setTimestamp(3,endTime);
            pstat.setBigDecimal(4,usedkW);
            pstat.setBigDecimal(5,costPerKwh);
            pstat.setTime(6,duration);
            pstat.setBigDecimal(7,totalCost);
            pstat.setInt(8,accountid);



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
