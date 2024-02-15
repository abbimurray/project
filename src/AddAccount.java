
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class  AddAccount {
    public static void main(String[] args) {
        //database url
        final String DATABASE_URL = "jdbc:mysql://localhost:3306/EVCharging";
        Connection connection = null;
        PreparedStatement pstat = null;

        String firstname = "John";
        String lastname = "O'Brien";
        String email ="obrien@gmail.com";
        String phone ="(086)8578633";
        String password= "728Vjna?";
        double currentBalance =60.0;
        String address = "124 the drive, castlemartin lodge, naas, Co.Kildare";

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

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}

