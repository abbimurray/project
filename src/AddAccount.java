
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class  AddAccount {
    public static void main(String[] args) {
        //database url
        final String DATABASE_URL = "jdbc:mysql://localhost:3306/EVCharging";
        Connection connection = null;
        PreparedStatement pstat = null;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter First Name:");
        String firstname = scanner.nextLine();

        System.out.println("Enter Last Name:");
        String lastname = scanner.nextLine();

        System.out.println("Enter Email:");
        String email = scanner.nextLine();

        System.out.println("Enter Phone:");
        String phone = scanner.nextLine();

        System.out.println("Enter Password:");
        String password= scanner.nextLine();

        System.out.println("Enter Current Balance:");
        double currentBalance = scanner.nextDouble();

        System.out.println("Enter Address:");
        String address = scanner.nextLine();

        /*AccountId is primary key + is auto incremented so does it need to also be included as variable or will it just automatically increment for each new account created */


        int i=0;

        try {
            //establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, "root", "pknv!47A");

            //create prepared statement for inserting into table
            pstat = connection.prepareStatement("INSERT INTO account(FirstName,LastName, Email, Phone,Password, CurrentBalance,Address) VALUES (?,?,?,?,?,?,?)");
            pstat.setString(1, firstname);
            pstat.setString(2, lastname);
            pstat.setString(3, email);
            pstat.setString(4, phone);
            pstat.setString(5, password);
            pstat.setDouble(6, currentBalance);
            pstat.setString(7, address);

            //insert data into database
            i = pstat.executeUpdate();//if successful will return value of 1
            System.out.println(i + " :record successfully added to the database");

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        try {
            pstat.close();
            connection.close();
            // Close the Scanner
            scanner.close();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}

