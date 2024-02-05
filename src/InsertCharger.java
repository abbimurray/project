/*CLASS FOR ADDING A charger*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertCharger{
    public static void main(String[] args) {
        //database url
        final String DATABASE_URL = "jdbc:mysql://localhost:3306/EVCharging";
        Connection connection = null;
        PreparedStatement pstat = null;
        String chargerId= "";
        String status = "";
        int kw= 0;
        double costPerKwh= 0.0;
        /*as addressId is auto incremented do i add it here or not?*/
        /*how do i not hardcode? do i need a driver that connects to all of these classes?*/
        int i=0;


        try {
            //establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, "root", "pknv!47A");

            //create prepared statement for inserting into table
            pstat = connection.prepareStatement("INSERT INTO charger(ChargerID,Status,kW,CostPerKWh) VALUES (?,?,?,?)");
            pstat.setString(1, chargerId);
            pstat.setString(2, status);
            pstat.setInt(3, kw);
            pstat.setDouble(4, costPerKwh);


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

