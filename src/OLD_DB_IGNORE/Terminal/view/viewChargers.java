package OLD_DB_IGNORE.Terminal.view;

/*works*/

import java.sql.*;

public class viewChargers {
    public static void main( String[] args ){

        // database URL
        final String DATABASE_URL = "jdbc:mysql://localhost:3306/EVCharging";
        Connection connection = null;
        PreparedStatement pstat= null;
        ResultSet resultSet = null ;

        try
        {

            // establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, "root", "pknv!47A" );
            // create Prepared Statement for querying data in the table
            pstat = connection.prepareStatement("SELECT *  FROM chargers");
            // query data in the table
            resultSet = pstat.executeQuery();
            // process query results
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount(); System.out. println ( "chargers Table of EV Charging Database:\n" );
            for ( int i = 1; i <= numberOfColumns; i++ ) System.out. print (metaData.getColumnName( i ) + "\t"); System.out. println () ;

            while( resultSet .next() ){
                for ( int i = 1; i <= numberOfColumns; i++ ) System.out. print ( resultSet . getObject( i ) + "\t\t"); System.out. println () ;
            }
        }
        catch(SQLException sqlException ) { sqlException . printStackTrace () ;
        }
        finally {
            try {
                resultSet . close () ;
                pstat . close () ; connection . close () ; }
            catch (Exception exception){ exception . printStackTrace () ;
            } }
    } // end main
}
