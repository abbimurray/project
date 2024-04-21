package OLD_DB_IGNORE.Terminal.delete;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class deleteAvailability {
    public static void main(String[] args ) {
        final String DATABASE_URL =  "jdbc:mysql://localhost:3306/EVCharging";
        // database URL
        Connection connection = null;
        PreparedStatement pstat = null;
        int i =0;

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the chargerID of the entry you want to delete:");
        int chargerID = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.println("Enter the startTime of the entry you want to delete (YYYY-MM-DD HH:MM:SS):");
        String startTimeStr = scanner.nextLine();
        LocalDateTime startTime = LocalDateTime.parse(startTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); // Parse the input string to LocalDateTime with the specified pattern


        try
        {
            // establish connection to database
            connection = DriverManager.getConnection( DATABASE_URL, "root", "pknv!47A" );

            // create Prepared Statement for deleting data from the table
            pstat = connection.prepareStatement("Delete From availability Where chargerID=? and startTime= ?" );
            pstat . setInt(1,chargerID);
            pstat.setObject(2, startTime);


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
