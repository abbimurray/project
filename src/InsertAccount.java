/*insert class for adding account*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertAccount {
    public static void main(String[] args) {
        //database url
        final String DATABASE_URL = "jdbc:mysql://localhost:3306/EVCharging";
        Connection connection = null;
        PreparedStatement pstat = null;
        String firstname = "Lisa";
        String lastname = "Smith";
        String email ="lisasmith@mail.com";
        String phone ="(087)1398088";
        String password= "waumn!08Z";
        double currentBalance =20.0;
        int addressID = 1;


        int i=0;


        try {
            //establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, "root", "pknv!47A");

            //create prepared statement for inserting into table
            pstat = connection.prepareStatement("INSERT INTO account(FirstName,LastName, Email, Phone,Password, CurrentBalance,AddressID) VALUES (?,?,?,?,?,?,?)");
            pstat.setString(1, firstname);
            pstat.setString(2, lastname);
            pstat.setString(3, email);
            pstat.setString(4, phone);
            pstat.setString(5, password);
            pstat.setDouble(6, currentBalance);
            pstat.setInt(7, addressID);

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
