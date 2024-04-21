package OLD_DB_IGNORE.ignore_oldDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateChargerStatus {
    public static void main(String [] args ) {


        final String DATABASE_URL = "jdbc:mysql://localhost:3306/EVCharging";

        //need to stop hardcoding these values in here - use driver with scanner?
        String chargerId= "C5PHZ";
        String status = "available";
        String type="Chademo";

        Connection connection = null;
        PreparedStatement pstat = null;
        int i =0;

        try {
            // establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, "root","pknv!47A" );
            // create Prepared Statement for updating ALL data in the table
            pstat = connection.prepareStatement("Update charger SET status=?  Where chargerId=? AND type=? ");
            pstat . setString (1, status);
            pstat.setString(2, chargerId);
            pstat.setString(3,type);

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
