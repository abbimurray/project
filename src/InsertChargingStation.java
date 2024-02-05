import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertChargingStation {
    public static void main(String[] args) {
        //database url
        final String DATABASE_URL = "jdbc:mysql://localhost:3306/EVCharging";
        Connection connection = null;
        PreparedStatement pstat = null;
        String chargerId= "";
        String county = "";
        int css=0;
        int chademo=0;
        int fastAc=0;
        int acSocket=0;
        int cssKws=0;
        int chademoKws=0;
        int fastAcKws=0;
        int acSocketKws=0;
        double latitude=0;
        double longitude=0;
        int addressId=0;
        String Address="";/*should address be in this table? can you use address id to link this table?*/

        /*how do i not hardcode? do i need a driver that connects to all of these classes?*/
        int i=0;


        try {
            //establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, "root", "pknv!47A");

            //create prepared statement for inserting into table
            pstat = connection.prepareStatement("INSERT INTO chargingstation(County,CSS,Chademo,FastAC,ACSocket,CSS_kWs,Chademo_kWs,ACFast_kWs,ACSocket_kWs,Latitude,Longitude,AddressID,chargerID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
            pstat.setString(1, county);
            pstat.setInt(2, css);
            pstat.setInt(3, chademo);
            pstat.setInt(4, fastAc);
            pstat.setInt(5, acSocket);
            pstat.setInt(6,cssKws);
            pstat.setInt(7, chademoKws);
            pstat.setInt(8,fastAcKws);
            pstat.setInt(9, acSocketKws);
            pstat.setDouble(10, latitude);
            pstat.setDouble(11, longitude);
            pstat.setInt(12, addressId);
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
