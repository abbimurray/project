package ADD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class addUserAccounts {
    public static void main(String[] args) {
        //database url
        final String DATABASE_URL = "jdbc:mysql://localhost:3306/EVCharging";
        Connection connection = null;
        PreparedStatement pstat = null;

        Scanner scanner = new Scanner(System.in);
        // inputs
        //inputs will be replaced - insert through gui
        //user id is auto incremented- pri key- int value
        System.out.println("Enter username:");
        String username = scanner.nextLine();

        System.out.println("Enter Password:");
        String password = scanner.nextLine();

        System.out.println("Enter  Email:");
        String email = scanner.nextLine();


        System.out.println("Enter Payment Method :");
        String payMethod = scanner.nextLine();


        int i=0;


        try {
            //establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, "root", "pknv!47A");

            //create prepared statement for inserting into table
            pstat = connection.prepareStatement("INSERT INTO user_accounts (username, password, email, paymentMethod) VALUES (?,?,?,?)");
            pstat.setString(1, username);
            pstat.setString(2, password);
            pstat.setString(3, email);
            pstat.setString(4, payMethod);


            //insert data into database
            i = pstat.executeUpdate();//if successful will return value of 1
            System.out.println(i + " :record successfully added to the database (in the user_accounts table).");


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
