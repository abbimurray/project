/*student name: abigail murray
 * student number: C00260073*/
/*for inserting/ adding charging station */

/*NB: SOME CHARGING STATIONS WILL HAVE MORE THAN 1 CHARGER ID'S AS THEY HAVE MORE THAN ONE CHARGER, HOW SHOULD THIS BE DEALT WITH*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddChargingStation {
    public static void main(String[] args) {
        //database url
        final String DATABASE_URL = "jdbc:mysql://localhost:3306/EVCharging";
        Connection connection = null;
        PreparedStatement pstat = null;
        String chargerId= "TEV016a" ;
        String county = "Carlow";
        String address="Tesco Tullow, Abbey st, Templeowen,Tullow";
        int ccs=0;
        int chademo=0;
        int fastAc=0;
        int acSocket=2;
        int ccsKws=0;
        int chademoKws=0;
        int fastAcKws=0;
        int acSocketKws=22;
        double latitude=52.800607;
        double longitude= -6.738443;


        int i=0;


        try {
            //establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, "root", "pknv!47A");

            //create prepared statement for inserting into table
            pstat = connection.prepareStatement("INSERT INTO chargingstation(County,ccs,Chademo,FastAC,ACSocket,ccs_kWs,Chademo_kWs,ACFast_kWs,ACSocket_kWs,Latitude,Longitude,Address,chargerID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
            pstat.setString(1, county);
            pstat.setInt(2, ccs);
            pstat.setInt(3, chademo);
            pstat.setInt(4, fastAc);
            pstat.setInt(5, acSocket);
            pstat.setInt(6,ccsKws);
            pstat.setInt(7, chademoKws);
            pstat.setInt(8,fastAcKws);
            pstat.setInt(9, acSocketKws);
            pstat.setDouble(10, latitude);
            pstat.setDouble(11, longitude);
            pstat.setString(12, address);
            pstat.setString(13, chargerId);



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
