/*changes the cardnumber */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdatePaymentDetails {

    public static void main(String[] args) {


        final String DATABASE_URL = "jdbc:mysql://localhost:3306/EVCharging";

        //need to stop hardcoding these values in here - use driver with scanner?
        String nameOnCard = "lisa Smith";
        String cardNumber = "2987769078653199";
        int cvv = 237;

        Connection connection = null;
        PreparedStatement pstat = null;
        int i = 0;

        try {
            // establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, "root", "pknv!47A");
            // create Prepared Statement for updating ALL data in the table
            pstat = connection.prepareStatement("Update paymentDetails SET CardNumber=?  Where NameOnCard=? AND CVV=?");
            pstat.setString(1, cardNumber);
            pstat.setString(2, nameOnCard);
            pstat.setInt(3, cvv);

            //Update data in the table
            i = pstat.executeUpdate();
            System.out.println(i + " record successfully updated in the database table . ");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                pstat.close();
                connection.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}