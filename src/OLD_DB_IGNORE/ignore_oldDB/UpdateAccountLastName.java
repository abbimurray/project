package OLD_DB_IGNORE.ignore_oldDB;/*student name:abigail murray
student number:C00260073
FOR UPDATING ACCOUNT last name
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


    public class UpdateAccountLastName {
        public static void main(String [] args ) {


            final String DATABASE_URL = "jdbc:mysql://localhost:3306/EVCharging";

            String lastName="Murphy";
            int accountId=3;


            Connection connection = null;
            PreparedStatement pstat = null;
            int i =0;

            try {
                // establish connection to database
                connection = DriverManager.getConnection(DATABASE_URL, "root","pknv!47A" );
                // create Prepared Statement for updating ALL data in the table
                pstat = connection.prepareStatement("Update account SET LastName=?  Where AccountID=?");
                pstat . setString (1, lastName);
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
    }// end class

