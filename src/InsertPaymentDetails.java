/*FOR ADDING PAYMENT DETAILS*/
/*NB: NEED TO WORK OUT HOW THE PAYMENT DETAILS IS CONNECTED TO THE ACCOUNT??*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
public class InsertPaymentDetails {
    public static void main(String[] args) {
        //database url
        final String DATABASE_URL = "jdbc:mysql://localhost:3306/EVCharging";
        Connection connection = null;
        PreparedStatement pstat = null;

        String nameOnCard="lisa Smith";
        String cardNumber= "2987769078651678";
        int cvv=237;
        Date expiry = Date.valueOf("2027-06-01"); // Use Date.valueOf to convert a string to a SQL date

        /*how do i not hardcode? do i need a driver that connects to all of these classes?*/
        int i=0;


        try {
            //establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, "root", "pknv!47A");

            //create prepared statement for inserting into table
            pstat = connection.prepareStatement("INSERT INTO PaymentDetails (NameOnCard,CardNumber,Expiry,CVV) VALUES (?,?,?,?)");
            pstat.setString(1, nameOnCard);
            pstat.setString(2, cardNumber);
            pstat.setDate(3, expiry);
            pstat.setInt(4, cvv);



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
