import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertTransaction {
    public static void main(String[] args) {
        //database url
        final String DATABASE_URL = "jdbc:mysql://localhost:3306/EVCharging";
        Connection connection = null;
        PreparedStatement pstat = null;

        String nameOnCard=" ";
        int cardNumber=0;
        String expiry =""; /*expiry is a date data type in database so how is this dealt with?*/
        int cvv=0;

        /*corresponding data types for dates and times??*/
        /*should transaction id be included here? it is auto incremented*/
        /*how do i not hardcode? do i need a driver that connects to all of these classes?*/
        int i=0;


        try {
            //establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, "root", "pknv!47A");

            //create prepared statement for inserting into table
            pstat = connection.prepareStatement("INSERT INTO Transaction (NameOnCard,CardNumber,Expiry,CVV) VALUES (?,?,?,?)");
            pstat.setString(1, nameOnCard);
            pstat.setInt(2, cardNumber);
            pstat.setString(3, expiry);
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
