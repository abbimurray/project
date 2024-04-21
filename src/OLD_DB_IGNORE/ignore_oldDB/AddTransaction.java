package OLD_DB_IGNORE.ignore_oldDB;/*student name: abigail murray
 * student number: C00260073*/
/*FOR ADDING transaction DETAILS*/
/*nb: connecting account id and chargerid?*/
/*total cost method*/

import java.math.BigDecimal;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

    public class AddTransaction {
        public static void main(String[] args) throws ParseException {
            final String DATABASE_URL = "jdbc:mysql://localhost:3306/EVCharging";
            Connection connection = null;
            PreparedStatement pstat = null;
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter account id:");
            int accountid = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            System.out.println("Enter date of transaction (dd/mm/yyyy):");
            String dateInput = scanner.nextLine();
            java.util.Date utilDate = new SimpleDateFormat("dd/MM/yyyy").parse(dateInput);
            Date dateOfTransaction = new Date(utilDate.getTime());

            System.out.println("Enter start time of transaction (yyyy-mm-dd hh:mm:ss):");
            Timestamp startTime = Timestamp.valueOf(scanner.nextLine());
            System.out.println("Enter end time of transaction (yyyy-mm-dd hh:mm:ss):");
            Timestamp endTime = Timestamp.valueOf(scanner.nextLine());

            // Calculate the duration in milliseconds
            long durationMs = endTime.getTime() - startTime.getTime();
            // Calculate the duration in hours
            double durationHours = (double) durationMs / (1000 * 60 * 60);
            System.out.println("duration in hours" + durationHours);
            // Calculate the used kilowatt-hours
            double powerConsumptionRate = 50.0;
            BigDecimal usedkW = BigDecimal.valueOf(durationHours * powerConsumptionRate);
            System.out.println("Used kWh: " + usedkW);

            // Calculate total cost
            System.out.println("Enter cost per Kwh:");
            BigDecimal costPerKwh = BigDecimal.valueOf(scanner.nextDouble());
            BigDecimal totalCost = usedkW.multiply(costPerKwh);
            System.out.println("Total Cost: " + totalCost);

            int i = 0;

            try {
                connection = DriverManager.getConnection(DATABASE_URL, "root", "pknv!47A");
                pstat = connection.prepareStatement("INSERT INTO transaction (Date,StartTime,Endtime, usedkW,CostPerKWH, duration,TotalCost, accountid) VALUES (?,?,?,?,?,?,?,?)");
                pstat.setDate(1, dateOfTransaction);
                pstat.setTimestamp(2, startTime);
                pstat.setTimestamp(3, endTime);
                pstat.setBigDecimal(4, usedkW);
                pstat.setBigDecimal(5, costPerKwh);
                pstat.setLong(6, durationMs); // Set duration in milliseconds
                pstat.setBigDecimal(7, totalCost);
                pstat.setInt(8, accountid);

                i = pstat.executeUpdate();
                System.out.println(i + " :record successfully added to the database");

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
    }


    /*public static void main(String[] args) throws ParseException {
        //database url
        final String DATABASE_URL = "jdbc:mysql://localhost:3306/EVCharging";
        Connection connection = null;
        PreparedStatement pstat = null;
        Scanner scanner = new Scanner(System.in);
        // inputs
        //inputs will be replaced - insert through gui

        System.out.println("Enter account id:");
        int accountid = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.println("Enter date of transaction (dd/mm/yyyy):");
        String dateInput = scanner.nextLine();
        java.util.Date utilDate = new SimpleDateFormat("dd/MM/yyyy").parse(dateInput);
        Date dateOfTransaction = new Date(utilDate.getTime());

        System.out.println("Enter start time of transaction (yyyy-mm-dd hh:mm:ss):");
        Timestamp startTime = Timestamp.valueOf(scanner.nextLine());
        System.out.println("Enter end time of transaction (yyyy-mm-dd hh:mm:ss):");
        Timestamp endTime=  Timestamp.valueOf(scanner.nextLine());



        //calculate duration
        // Calculate the duration in milliseconds
        long durationMs = endTime.getTime() - startTime.getTime();
        // Calculate the duration in hours, minutes, and seconds
        long seconds = durationMs / 1000;
        long hours = seconds / 3600;
        seconds = seconds % 3600;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        // Create a Time object representing the duration
        Time duration = new Time(hours, minutes, seconds);
        System.out.println("Duration: " + duration);

        // Calculate the duration in hours
        double durationHours = (double) durationMs / (1000 * 60 * 60); // Convert duration from milliseconds to hours

        // Calculate the used kilowatt-hours
        double powerConsumptionRate = 50.0; // Set the power consumption rate
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
/*

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
}*/
