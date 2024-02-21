package ignore_oldDB;/*not working yet as it is connected to chargingstation so wont let you delete charger as its connected to chargingstation?*/
/*works if you first delete the charging station then go and delte the charger*/
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class DeleteCharger {
    public static void main(String[] args ) {
        final String DATABASE_URL =  "jdbc:mysql://localhost:3306/EVCharging";
// database URL
        Connection connection = null;
        PreparedStatement pstat = null;
        int i =0;


        Scanner scanner = new Scanner(System.in);

        //inputs will be replaced - insert through gui
        System.out.println("Enter charger id of charger you wish to delete:");
        String chargerID= scanner.nextLine();




        try
        {
            // establish connection to database
            connection = DriverManager.getConnection( DATABASE_URL, "root", "pknv!47A" );

            // create Prepared Statement for deleting data from the table
            pstat = connection.prepareStatement("Delete From charger Where ChargerID=?" );
            pstat . setString (1, chargerID);

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
