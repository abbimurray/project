package model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ChargingStationModel {
    // Database credentials
    private final String DATABASE_URL = "jdbc:mysql://localhost:3306/EVCharging";
    private final String DATABASE_USER = "root";
    private final String DATABASE_PASSWORD = "pknv!47A";


    public List<String> getDistinctCounties() {
        List<String> counties = new ArrayList<>();
        String sql = "SELECT DISTINCT county FROM charging_stations ORDER BY county ASC";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String county = rs.getString("county");
                if(county != null && !county.trim().isEmpty()) { // Check for null or empty to avoid adding them
                    counties.add(county);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return counties;
    }

    public List<ChargingStation> getStationsByCounty(String county) {
        List<ChargingStation> stations = new ArrayList<>();
        String sql = "SELECT * FROM charging_stations WHERE county = ? ORDER BY address ASC";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, county);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                ChargingStation station = new ChargingStation();
                station.setStationID(rs.getInt("stationID"));
                station.setCounty(rs.getString("county"));
                station.setAddress(rs.getString("address"));
                station.setNumberOfChargers(rs.getInt("numberOfChargers"));
                stations.add(station);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stations;
    }


}