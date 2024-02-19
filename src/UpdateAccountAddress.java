import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class UpdateAccountAddress {
    public static void main(String [] args ) {


        final String DATABASE_URL = "jdbc:mysql://localhost:3306/EVCharging";

        Connection connection = null;
        PreparedStatement pstat = null;
        int i =0;

        Scanner scanner = new Scanner(System.in);
        // inputs
        //inputs will be replaced - insert through gui
        System.out.println("Enter accountId:");
        int accountId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter new address:");
        String address = scanner.nextLine();


        try {
            // establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, "root","pknv!47A" );
            // create Prepared Statement for updating ALL data in the table
            pstat = connection.prepareStatement("Update account SET Address=?  Where AccountID=?");
            pstat . setString (1, address);
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
