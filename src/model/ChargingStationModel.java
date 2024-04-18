package model;


import controller.ChargerRatePower;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

public class ChargingStationModel {
    // Database credentials
    private final String DATABASE_URL = "jdbc:mysql://localhost:3306/EVCharging";
    private final String DATABASE_USER = "root";
    private final String DATABASE_PASSWORD = "pknv!47A";



    //used in FindChargingStations class
    public List<String> getDistinctCounties() {
        List<String> counties = new ArrayList<>();
        String sql = "SELECT DISTINCT county FROM charging_stations ORDER BY county ASC";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String county = rs.getString("county");
                if (county != null && !county.trim().isEmpty()) { // Check for null or empty to avoid adding them
                    counties.add(county);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return counties;
    }


    //used in FindChargingStations class
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


    //used in stationdetails
    public List<Charger> getChargersByStationId(int stationId) {
        List<Charger> chargers = new ArrayList<>();
        String sql = "SELECT * FROM chargers WHERE stationID = ?";
        try (Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, stationId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Charger charger = new Charger();
                charger.setChargerID(rs.getInt("chargerID"));
                charger.setChargerType(rs.getString("chargerType"));
                charger.setStationID(rs.getInt("stationID"));
                charger.setStatus(rs.getString("status"));
                charger.setKw(rs.getInt("kw"));
                charger.setCostPerKWH(rs.getBigDecimal("costPerKWH"));
                chargers.add(charger);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chargers;
    }

    //method to check charger availability
    public boolean checkChargerAvailability(int chargerID) {
        String query = "SELECT status FROM chargers WHERE chargerID = ?";
        try (Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, chargerID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String status = rs.getString("status");
                    return "Available".equals(status);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    //method to update the status of the charger
    public boolean updateChargerStatus(int chargerID, String status) {
        String query = "UPDATE chargers SET status = ? WHERE chargerID = ?";
        try (Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, chargerID);
            int updated = pstmt.executeUpdate();
            return updated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to update chargingTransactions record after the session has ended
    public void updateChargingTransaction(int transactionID, LocalDateTime endTime, BigDecimal energyConsumed, BigDecimal totalCost) {
        String query = "UPDATE chargingTransactions SET endTime = ?, energyConsumed = ?, totalCost = ? WHERE transactionID = ?";
        try (Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setTimestamp(1, java.sql.Timestamp.valueOf(endTime));
            pstmt.setBigDecimal(2, energyConsumed);
            pstmt.setBigDecimal(3, totalCost);
            pstmt.setInt(4, transactionID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Method to fetch the start time and charger ID for a transaction
    private LocalDateTime fetchStartTimeForTransaction(int transactionID) {
        String query = "SELECT startTime FROM chargingTransactions WHERE transactionID = ?";
        try (Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, transactionID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getTimestamp("startTime").toLocalDateTime();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Consider handling this case
    }


//method to get rate and power for calculating transaction cost
    public ChargerRatePower fetchChargerRateAndPower(int chargerID) {
        String query = "SELECT costPerKWH, kw FROM chargers WHERE chargerID = ?";
        try (Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, chargerID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    BigDecimal costPerKWH = rs.getBigDecimal("costPerKWH");
                    BigDecimal kw = rs.getBigDecimal("kw"); // Assuming the database 'kw' column can be treated directly as BigDecimal
                    return new ChargerRatePower(costPerKWH, kw);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    //duration of charging session needed for calculating the cost
    public BigDecimal calculateDurationHours(LocalDateTime startTime, LocalDateTime endTime) {
        long seconds = Duration.between(startTime, endTime).getSeconds();
        return BigDecimal.valueOf(seconds).divide(BigDecimal.valueOf(3600), 2, RoundingMode.HALF_UP);
    }


    //METHOD TO START A SESSION

    public int startSession(int chargerID, int customerID) {
        // Initialize transactionID to an invalid value
        int transactionID = -1;

        //update the charger status ti 'in-use' so that the other users cannot also use the same charger at this time
        //start newtransaction record with the start time, chargerid, customerid
        String updateStatusQuery = "UPDATE chargers SET status = 'In-Use' WHERE chargerID = ?";
        String insertSessionQuery = "INSERT INTO chargingTransactions (startTime, chargerID, customerID) VALUES (NOW(), ?, ?)";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement updateStmt = conn.prepareStatement(updateStatusQuery);
             PreparedStatement insertStmt = conn.prepareStatement(insertSessionQuery, Statement.RETURN_GENERATED_KEYS)) {

            // Update charger status
            updateStmt.setInt(1, chargerID);
            updateStmt.executeUpdate();

            // Insert new session record
            insertStmt.setInt(1, chargerID);
            insertStmt.setInt(2, customerID);
            insertStmt.executeUpdate();

            // Retrieve and return the generated transaction ID
            try (ResultSet generatedKeys = insertStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    transactionID = generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionID;
    }

    public void endSession(int transactionID) {
        // Fetch start time for the transaction
        LocalDateTime startTime = fetchStartTimeForTransaction(transactionID);

        // First, fetch the chargerID associated with this transaction
        int chargerID = fetchChargerIDForTransaction(transactionID);
        if (chargerID == -1) {
            System.out.println("Charger ID for transaction could not be found.");
            return;
        }

        // Calculate duration, energy consumed, and total cost
        LocalDateTime endTime = LocalDateTime.now();
        BigDecimal durationHours = calculateDurationHours(startTime, endTime);
        ChargerRatePower ratePower = fetchChargerRateAndPower(chargerID);
        BigDecimal energyConsumed = ratePower.getKw().multiply(durationHours);
        BigDecimal totalCost = energyConsumed.multiply(ratePower.getCostPerKWH());

        // Update the transaction and charger status
        updateChargingTransaction(transactionID, endTime, energyConsumed, totalCost);
        updateChargerStatus(chargerID, "Available");
    }

    private int fetchChargerIDForTransaction(int transactionID) {
        String query = "SELECT chargerID FROM chargingTransactions WHERE transactionID = ?";
        try (Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, transactionID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("chargerID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Indicates that the charger ID was not found
    }
}

   /* public void endSession(int transactionID) {
        // Fetch start time for the transaction
        LocalDateTime startTime = fetchStartTimeForTransaction(transactionID);

        // Calculate duration, energy consumed, and total cost
        LocalDateTime endTime = LocalDateTime.now();
        BigDecimal durationHours = calculateDurationHours(startTime, endTime);
        ChargerRatePower ratePower = fetchChargerRateAndPower(chargerID); // Ensure you have the correct chargerID
        BigDecimal energyConsumed = ratePower.getKw().multiply(durationHours);
        BigDecimal totalCost = energyConsumed.multiply(ratePower.getCostPerKWH());

        // Update the transaction and charger status
        updateChargingTransaction(transactionID, endTime, energyConsumed, totalCost);
        updateChargerStatus(chargerID, "Available");
    }
}*/
// End session method
    /*public void endSession(int transactionID) {
        LocalDateTime startTime = fetchStartTimeForTransaction(transactionID);
        LocalDateTime endTime = LocalDateTime.now();
        BigDecimal durationHours = calculateDurationHours(startTime, endTime);

        // Assume chargerID is fetched or passed into this method
        int chargerID = 1; // Placeholder value
        ChargerRatePower ratePower = fetchChargerRateAndPower(chargerID);

        BigDecimal energyConsumed = ratePower.getKw().multiply(durationHours);
        BigDecimal totalCost = energyConsumed.multiply(ratePower.getCostPerKWH());

        updateChargingTransaction(transactionID, endTime, energyConsumed, totalCost);
        updateChargerStatus(chargerID, "Available");
    }*/




/* public List<Charger> getChargersByStationId(int stationId) {
        List<Charger> chargers = new ArrayList<>();
        String sql = "SELECT * FROM chargers WHERE stationID = ?";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, stationId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Charger charger = new Charger();
                charger.setChargerID(rs.getInt("chargerID"));
                charger.setChargerType(rs.getString("chargerType"));
                charger.setStationID(rs.getInt("stationID"));
                charger.setStatus(rs.getString("status"));
                charger.setKw(rs.getInt("kw"));
                charger.setCostPerKWH(rs.getBigDecimal("costPerKWH"));

                chargers.add(charger);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return chargers;
    }*/