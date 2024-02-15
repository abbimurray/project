import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteChargingStation {
    public static void main(String[] args ) {
        final String DATABASE_URL =  "jdbc:mysql://localhost:3306/EVCharging";
// database URL
        Connection connection = null;
        PreparedStatement pstat = null;
        int i =0;
        String address="";
        try
        {
            // establish connection to database
            connection = DriverManager.getConnection( DATABASE_URL, "root", "pknv!47A" );

            // create Prepared Statement for deleting data from the table
            pstat = connection.prepareStatement("Delete From chargingStation Where Address=?" );
            pstat . setString(1, address);

            // delete data from the table
            i = pstat.executeUpdate();
            System.out. println ( i + " record successfully removed from the table . ") ;

        }
        catch(SQLException sqlException )
        {
            sqlException . printStackTrace () ;
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
