/*works with scanner*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class DeletePaymentDetails {
    public static void main(String[] args) {
        final String DATABASE_URL = "jdbc:mysql://localhost:3306/EVCharging";
        Connection connection = null;
        PreparedStatement pstat = null;
        int i = 0;

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter card number of card you wish to delete:");
        String cardNumber = scanner.nextLine();

        try {
            // establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, "root", "pknv!47A");

            // create Prepared Statement for deleting data from the table
            pstat = connection.prepareStatement("DELETE FROM paymentDetails WHERE CardNumber=?");
            pstat.setString(1, cardNumber);

            // delete data from the table
            i = pstat.executeUpdate();
            System.out.println(i + " record successfully removed from the table.");

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
