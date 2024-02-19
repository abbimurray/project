/*student name: abigail murray
 * student number: C00260073*/
/*FOR ADDING PAYMENT DETAILS*/
/*NB: NEED TO WORK OUT HOW THE PAYMENT DETAILS IS CONNECTED TO THE ACCOUNT?? OR TO TRANSACTION*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.util.Scanner;

public class AddPaymentDetails {
    public static void main(String[] args) {
        //database url
        final String DATABASE_URL = "jdbc:mysql://localhost:3306/EVCharging";
        Connection connection = null;
        PreparedStatement pstat = null;
        Scanner scanner = new Scanner(System.in);
        // inputs
        //inputs will be replaced - insert through gui
        System.out.println("Enter name on card:");
        String nameOnCard = scanner.nextLine();

        System.out.println("Enter card number:");
        String cardNumber = scanner.nextLine();

        System.out.println("Enter cvv:");
        int cvv = scanner.nextInt();

        System.out.println("Enter Expiry date:");
        Date expiry = Date.valueOf(scanner.nextLine());// Use Date.valueOf to convert a string to a SQL date
       /* Date expiry = Date.valueOf("2027-06-01"); */
        int i=0;


        try {
            //establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, "root", "pknv!47A");

            //create prepared statement for inserting into table
            pstat = connection.prepareStatement("INSERT INTO paymentDetails (NameOnCard,CardNumber,Expiry,CVV) VALUES (?,?,?,?)");
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
