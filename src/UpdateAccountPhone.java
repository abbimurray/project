import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateAccountPhone {
    public static void main(String [] args ) {


        final String DATABASE_URL = "jdbc:mysql://localhost:3306/EVCharging";

        String phone="087 6547899";
        int accountId=3;


        Connection connection = null;
        PreparedStatement pstat = null;
        int i =0;

        try {
            // establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, "root","pknv!47A" );
            // create Prepared Statement for updating ALL data in the table
            pstat = connection.prepareStatement("Update account SET Phone=?  Where AccountID=?");
            pstat . setString (1, phone);
            pstat.setInt(2, accountId);

            //Update data in the table
            i = pstat.executeUpdate();
            System.out. println ( i + " record successfully updated in the database table . ") ;
        }

        catch(SQLException sqlException )
        { sqlException . printStackTrace () ;
        }
        finally
        {
            try {
                pstat . close () ; connection . close () ;
            }
            catch ( Exception exception )
            {
                exception . printStackTrace () ;
            }
        }
    } // end main
}
