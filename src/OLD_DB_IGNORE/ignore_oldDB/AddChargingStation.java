package OLD_DB_IGNORE.ignore_oldDB;/*student name: abigail murray
 * student number: C00260073*/
/*for inserting/ adding charging station */
/*will work once the charger whose charger id you enter already exist in system*/
/*NB: SOME CHARGING STATIONS WILL HAVE MORE THAN 1 CHARGER ID'S AS THEY HAVE MORE THAN ONE CHARGER, HOW SHOULD THIS BE DEALT WITH*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class AddChargingStation {
    public static void main(String[] args) {
        //database url
        final String DATABASE_URL = "jdbc:mysql://localhost:3306/EVCharging";
        Connection connection = null;
        PreparedStatement pstat = null;
        Scanner scanner = new Scanner(System.in);
        // inputs
        //inputs will be replaced - insert through gui
        System.out.println("Enter ChargerID:");
        String chargerId = scanner.nextLine();

        System.out.println("Enter County:");
        String county = scanner.nextLine();

        System.out.println("Enter Address:");
        String address = scanner.nextLine();

        System.out.println("Enter  number of ccs:");
        int ccs = scanner.nextInt();

        System.out.println("Enter  number of chademo:");
        int chademo = scanner.nextInt();

        System.out.println("Enter  number of fast ac:");
        int fastAc = scanner.nextInt();

        System.out.println("Enter  number of ac socket:");
        int acSocket = scanner.nextInt();

        System.out.println("Enter ccs kws:");
        int ccsKws = scanner.nextInt();

        System.out.println("Enter chademo kws:");
        int chademoKws = scanner.nextInt();

        System.out.println("Enter fast ac kws:");
        int fastAcKws = scanner.nextInt();

        System.out.println("Enter ac socket kws:");
        int acSocketKws = scanner.nextInt();

        System.out.println("Enter latitude:");
        double latitude = scanner.nextDouble();

        System.out.println("Enter longitude:");
        double longitude = scanner.nextDouble();



        int i=0;


        try {
            //establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, "root", "pknv!47A");

            //create prepared statement for inserting into table
            pstat = connection.prepareStatement("INSERT INTO chargingStation(County,ccs,Chademo,FastAC,ACSocket,ccs_kWs,Chademo_kWs,ACFast_kWs,ACSocket_kWs,Latitude,Longitude,Address,chargerID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
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
