package ignore_oldDB;/*changes the cardnumber */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class UpdatePaymentDetails {

    public static void main(String[] args) {


        final String DATABASE_URL = "jdbc:mysql://localhost:3306/EVCharging";

        //need to stop hardcoding these values in here - use driver with scanner?




        Connection connection = null;
        PreparedStatement pstat = null;
        int i = 0;

        Scanner scanner = new Scanner(System.in);

        //inputs will be replaced - insert through gui
        System.out.println("Enter name on the card you wish to update:");
        String nameOnCard= scanner.nextLine();
        System.out.println("Enter new card number:");
        String cardNumber = scanner.nextLine();
        System.out.println("Enter new cvv:");
        int cvv = scanner.nextInt();


        try {
            // establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, "root", "pknv!47A");
            // create Prepared Statement for updating ALL data in the table
            pstat = connection.prepareStatement("UPDATE paymentDetails SET CardNumber=?, CVV=? WHERE NameOnCard=?");
            pstat.setString(1, cardNumber);
            pstat.setInt(2, cvv);
            pstat.setString(3, nameOnCard);
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