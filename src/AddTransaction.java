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

public class AddTransaction {
    public static void main(String[] args) {
        //database url
        final String DATABASE_URL = "jdbc:mysql://localhost:3306/EVCharging";
        Connection connection = null;
        PreparedStatement pstat = null;

        Date dateOfTransaction = Date.valueOf("2024-02-06");// = Date.valueOf("2027-06-01"); // Use Date.valueOf to convert a string to a SQL date
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

        int accountid =3;

//pstat.setBigDecimal(9, decimalValue); // Use setBigDecimal to set the decimal value in the prepared statement*/

        /*corresponding data types for dates and times??*/
        /*should transaction id be included here? it is auto incremented*/
        /*how do i not hardcode? do i need a driver that connects to all of these classes?*/
        int i=0;


        try {
            //establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, "root", "pknv!47A");

            //create prepared statement for inserting into table
            pstat = connection.prepareStatement("INSERT INTO Transaction (Date,StartTime,Endtime, usedkW,CostPerKWH, duration,TotalCost, accountid) VALUES (?,?,?,?,?,?,?,?)");
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
