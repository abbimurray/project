package model;


import controller.ChargerRatePower;
import utils.DBConnection;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.Duration;

public class ChargingStationModel {

    // Get distinct counties for the FindChargingStations class
    public List<String> getDistinctCounties() {
        List<String> counties = new ArrayList<>();
        String sql = "SELECT DISTINCT county FROM charging_stations ORDER BY county ASC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String county = rs.getString("county");
                if (county != null && !county.trim().isEmpty()) {
                    counties.add(county);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return counties;
    }

    // Get stations by county for the FindChargingStations class
    public List<ChargingStation> getStationsByCounty(String county) {
        List<ChargingStation> stations = new ArrayList<>();
        String sql = "SELECT * FROM charging_stations WHERE county = ?";
        try (Connection conn = DBConnection.getConnection();
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

    // Get chargers by station ID for the StationDetails class
    public List<Charger> getChargersByStationId(int stationId) {
        List<Charger> chargers = new ArrayList<>();
        String sql = "SELECT * FROM chargers WHERE stationID = ?";
        try (Connection conn = DBConnection.getConnection();
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

    // Check charger availability
    public boolean checkChargerAvailability(int chargerID) {
        String query = "SELECT status FROM chargers WHERE chargerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, chargerID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return "Available".equals(rs.getString("status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Update the status of the charger
    public boolean updateChargerStatus(int chargerID, String status, LocalDateTime sessionStartTime, Integer customerID) {
        String query = "UPDATE chargers SET status = ?, sessionStartTime = ?, currentUserId = ? WHERE chargerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, status);
            pstmt.setTimestamp(2, sessionStartTime != null ? Timestamp.valueOf(sessionStartTime) : null);
            if (customerID == null) {
                pstmt.setNull(3, Types.INTEGER);
            } else {
                pstmt.setInt(3, customerID);
            }
            pstmt.setInt(4, chargerID);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // Update chargingTransactions record after the session has ended
    public void updateChargingTransaction(int transactionID, LocalDateTime endTime, BigDecimal energyConsumed, BigDecimal totalCost) {
        String query = "UPDATE chargingTransactions SET endTime = ?, energyConsumed = ?, totalCost = ? WHERE transactionID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setTimestamp(1, Timestamp.valueOf(endTime));
            pstmt.setBigDecimal(2, energyConsumed);
            pstmt.setBigDecimal(3, totalCost);
            pstmt.setInt(4, transactionID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Fetch the start time and charger ID for a transaction
    public LocalDateTime fetchStartTimeForTransaction(int transactionID) {
        String query = "SELECT startTime FROM chargingTransactions WHERE transactionID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, transactionID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getTimestamp("startTime").toLocalDateTime();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Consider proper error handling
    }

    // Calculate the duration of charging session needed for calculating the cost
    public BigDecimal calculateDurationHours(LocalDateTime startTime, LocalDateTime endTime) {
        long seconds = Duration.between(startTime, endTime).getSeconds();
        return BigDecimal.valueOf(seconds).divide(BigDecimal.valueOf(3600), 2, RoundingMode.HALF_UP);
    }


    public BigDecimal calculateEnergyConsumed(BigDecimal durationHours, int chargerID) {
        BigDecimal kw = fetchChargerKw(chargerID); // Method to fetch kW rating of the charger
        return durationHours.multiply(kw);
    }

    public BigDecimal calculateTotalCost(BigDecimal energyConsumed, int chargerID) {
        BigDecimal costPerKWH = fetchChargerCostPerKWH(chargerID); // Method to fetch cost per kWh for the charger
        return energyConsumed.multiply(costPerKWH);
    }

    // Helper methods to fetch kW and costPerKWH based on chargerID
    public BigDecimal fetchChargerKw(int chargerID) {
        String query = "SELECT kw FROM chargers WHERE chargerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, chargerID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return BigDecimal.valueOf(rs.getInt("kw"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO; // Default value if not found
    }

    public BigDecimal fetchChargerCostPerKWH(int chargerID) {
        String query = "SELECT costPerKWH FROM chargers WHERE chargerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, chargerID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal("costPerKWH");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO; // Default value if not found
    }

    public int createChargingTransaction(LocalDateTime startTime, int chargerID, int customerID, BigDecimal rate) {
        String sql = "INSERT INTO chargingTransactions (startTime, chargerID, customerID, rate) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setTimestamp(1, Timestamp.valueOf(startTime));
            pstmt.setInt(2, chargerID);
            pstmt.setInt(3, customerID);
            pstmt.setBigDecimal(4, rate);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating transaction failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);  // Return the new transaction ID
                } else {
                    throw new SQLException("Creating transaction failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;  // Return -1 if transaction creation fails
        }
    }

    /*public int startChargingSession(int chargerID, int customerID) {
        LocalDateTime sessionStartTime = LocalDateTime.now();
        BigDecimal rate = fetchChargerCostPerKWH(chargerID);  // Make sure this method is accessible

        if (updateChargerStatus(chargerID, "In-Use", sessionStartTime, customerID)) {
            return createChargingTransaction(sessionStartTime, chargerID, customerID, rate);
        }
        return -1;  // Indicate failure if the charger status couldn't be updated
    }*/

   /* public int createChargingTransaction(LocalDateTime sessionStartTime, int chargerID, int customerID, BigDecimal rate) {
        String sql = "INSERT INTO chargingTransactions (startTime, chargerID, customerID, rate) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setTimestamp(1, Timestamp.valueOf(sessionStartTime));
            pstmt.setInt(2, chargerID);
            pstmt.setInt(3, customerID);
            pstmt.setBigDecimal(4, rate);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating transaction failed, no rows affected.");
            }
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);  // Return the new transaction ID
                } else {
                    throw new SQLException("Creating transaction failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;  // Return -1 if transaction creation fails
    }*/

    /*public int createChargingTransaction(LocalDateTime sessionStartTime, int chargerID, int customerID, BigDecimal rate) {
        String sql = "INSERT INTO chargingTransactions (startTime, chargerID, customerID, rate) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setTimestamp(1, Timestamp.valueOf(sessionStartTime));
            pstmt.setInt(2, chargerID);
            pstmt.setInt(3, customerID);
            pstmt.setBigDecimal(4, rate);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating transaction failed, no rows affected.");
            }
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int transactionId = generatedKeys.getInt(1);
                    // Print statement to log the creation of the transaction
                    System.out.println("Transaction was created for transactionID " + transactionId + " using createChargingTransactionMethod");
                    return transactionId;  // Return the new transaction ID
                } else {
                    throw new SQLException("Creating transaction failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;  // Return -1 if transaction creation fails
    }*/




    /////

}//END CLASS


/*

version
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

    // Method to update chargingTransactions record
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
        return null; // Consider how you want to handle this case
    }

    public BigDecimal calculateDurationHours(LocalDateTime startTime, LocalDateTime endTime) {
        long seconds = Duration.between(startTime, endTime).getSeconds();
        return BigDecimal.valueOf(seconds).divide(BigDecimal.valueOf(3600), 2, RoundingMode.HALF_UP);
    }


    //METHOD TO START A SESSION

    public int startSession(int chargerID, int customerID) {
        // Initialize transactionID to an invalid value
        int transactionID = -1;
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












/////////////////////////////////////////////////////
/*package model;


import utils.DBConnection;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.Duration;

public class ChargingStationModel {

    // Get distinct counties for the FindChargingStations class
    public List<String> getDistinctCounties() {
        List<String> counties = new ArrayList<>();
        String sql = "SELECT DISTINCT county FROM charging_stations ORDER BY county ASC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String county = rs.getString("county");
                if (county != null && !county.trim().isEmpty()) {
                    counties.add(county);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return counties;
    }

    // Get stations by county for the FindChargingStations class
    public List<ChargingStation> getStationsByCounty(String county) {
        List<ChargingStation> stations = new ArrayList<>();
        String sql = "SELECT * FROM charging_stations WHERE county = ?";
        try (Connection conn = DBConnection.getConnection();
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

    // Get chargers by station ID for the StationDetails class
    public List<Charger> getChargersByStationId(int stationId) {
        List<Charger> chargers = new ArrayList<>();
        String sql = "SELECT * FROM chargers WHERE stationID = ?";
        try (Connection conn = DBConnection.getConnection();
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

    // Check charger availability
    public boolean checkChargerAvailability(int chargerID) {
        String query = "SELECT status FROM chargers WHERE chargerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, chargerID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return "Available".equals(rs.getString("status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Update the status of the charger
    public boolean updateChargerStatus(int chargerID, String status, LocalDateTime sessionStartTime, Integer customerID) {
        String query = "UPDATE chargers SET status = ?, sessionStartTime = ?, currentUserId = ? WHERE chargerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, status);
            pstmt.setTimestamp(2, sessionStartTime != null ? Timestamp.valueOf(sessionStartTime) : null);
            if (customerID == null) {
                pstmt.setNull(3, Types.INTEGER);
            } else {
                pstmt.setInt(3, customerID);
            }
            pstmt.setInt(4, chargerID);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }




    // Update chargingTransactions record after the session has ended
    public boolean updateChargingTransaction(int transactionID, LocalDateTime endTime, BigDecimal energyConsumed, BigDecimal totalCost) {
        String query = "UPDATE chargingTransactions SET endTime = ?, energyConsumed = ?, totalCost = ? WHERE transactionID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setTimestamp(1, Timestamp.valueOf(endTime));
            pstmt.setBigDecimal(2, energyConsumed);
            pstmt.setBigDecimal(3, totalCost);
            pstmt.setInt(4, transactionID);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    // Fetch the start time and charger ID for a transaction
    public LocalDateTime fetchStartTimeForTransaction(int transactionID) {
        String query = "SELECT startTime FROM chargingTransactions WHERE transactionID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, transactionID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getTimestamp("startTime").toLocalDateTime();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Consider proper error handling
    }

    // Calculate the duration of charging session needed for calculating the cost
    public BigDecimal calculateDurationHours(LocalDateTime startTime, LocalDateTime endTime) {
        long seconds = Duration.between(startTime, endTime).getSeconds();
        return BigDecimal.valueOf(seconds).divide(BigDecimal.valueOf(3600), 2, RoundingMode.HALF_UP);
    }


    public BigDecimal calculateEnergyConsumed(BigDecimal durationHours, int chargerID) {
        BigDecimal kw = fetchChargerKw(chargerID); // Method to fetch kW rating of the charger
        return durationHours.multiply(kw);
    }

    public BigDecimal calculateTotalCost(BigDecimal energyConsumed, int chargerID) {
        BigDecimal costPerKWH = fetchChargerCostPerKWH(chargerID); // Method to fetch cost per kWh for the charger
        return energyConsumed.multiply(costPerKWH);
    }

    // Helper methods to fetch kW and costPerKWH based on chargerID
    private BigDecimal fetchChargerKw(int chargerID) {
        String query = "SELECT kw FROM chargers WHERE chargerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, chargerID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return BigDecimal.valueOf(rs.getInt("kw"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO; // Default value if not found
    }

    private BigDecimal fetchChargerCostPerKWH(int chargerID) {
        String query = "SELECT costPerKWH FROM chargers WHERE chargerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, chargerID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal("costPerKWH");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO; // Default value if not found
    }
    public int startChargingSession(int chargerID, int customerID) {
        if (!checkChargerAvailability(chargerID)) {
            System.out.println("Charger is not available.");
            return -1;
        }

        LocalDateTime sessionStartTime = LocalDateTime.now();
        BigDecimal rate = fetchChargerCostPerKWH(chargerID);

        int transactionID = createChargingTransaction(sessionStartTime, chargerID, customerID, rate);
        if (transactionID == -1) {
            System.out.println("Failed to create charging transaction.");
            return -1;
        }

        if (!updateChargerStatus(chargerID, "In-Use", sessionStartTime, customerID)) {
            System.out.println("Failed to update charger status.");
            return -1;
        }

        return transactionID; // Successful transaction ID
    }
    public boolean endChargingSession(int chargerID, int transactionID) {
        try {
            LocalDateTime startTime = fetchStartTimeForTransaction(transactionID);
            if (startTime == null) {
                System.out.println("Start time not found for transaction ID: " + transactionID);
                return false;
            }

            LocalDateTime endTime = LocalDateTime.now();
            BigDecimal durationHours = calculateDurationHours(startTime, endTime);
            BigDecimal energyConsumed = calculateEnergyConsumed(durationHours, chargerID);
            BigDecimal totalCost = calculateTotalCost(energyConsumed, chargerID);

            if (!updateChargingTransaction(transactionID, endTime, energyConsumed, totalCost)) {
                System.out.println("Failed to update charging transaction for transaction ID: " + transactionID);
                return false;
            }

            if (!updateChargerStatus(chargerID, "Available", null, null)) {
                System.out.println("Failed to update charger status to 'Available' for charger ID: " + chargerID);
                return false;
            }

            System.out.println("Session ended successfully for charger " + chargerID + " with transaction ID " + transactionID);
            return true;
        } catch (Exception e) {
            System.out.println("Error ending session: " + e.getMessage());
            return false;
        }
    }

    public int createChargingTransaction(LocalDateTime sessionStartTime, int chargerID, int customerID, BigDecimal rate) {
        String sql = "INSERT INTO chargingTransactions (startTime, chargerID, customerID, rate) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setTimestamp(1, Timestamp.valueOf(sessionStartTime));
            pstmt.setInt(2, chargerID);
            pstmt.setInt(3, customerID);
            pstmt.setBigDecimal(4, rate);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("Creating transaction failed, no rows affected.");
                return -1;
            }
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    System.out.println("Creating transaction failed, no ID obtained.");
                    return -1;
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Error in creating transaction: " + e.getMessage());
            return -1;
        }
    }
}//END CLASS




/*package model;


import controller.ChargerRatePower;
import utils.DBConnection;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.Duration;

public class ChargingStationModel {

    // Get distinct counties for the FindChargingStations class
    public List<String> getDistinctCounties() {
        List<String> counties = new ArrayList<>();
        String sql = "SELECT DISTINCT county FROM charging_stations ORDER BY county ASC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String county = rs.getString("county");
                if (county != null && !county.trim().isEmpty()) {
                    counties.add(county);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return counties;
    }

    // Get stations by county for the FindChargingStations class
    public List<ChargingStation> getStationsByCounty(String county) {
        List<ChargingStation> stations = new ArrayList<>();
        String sql = "SELECT * FROM charging_stations WHERE county = ?";
        try (Connection conn = DBConnection.getConnection();
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

    // Get chargers by station ID for the StationDetails class
    public List<Charger> getChargersByStationId(int stationId) {
        List<Charger> chargers = new ArrayList<>();
        String sql = "SELECT * FROM chargers WHERE stationID = ?";
        try (Connection conn = DBConnection.getConnection();
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

    // Check charger availability
    public boolean checkChargerAvailability(int chargerID) {
        String query = "SELECT status FROM chargers WHERE chargerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, chargerID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return "Available".equals(rs.getString("status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Update the status of the charger
    public boolean updateChargerStatus(int chargerID, String status, LocalDateTime sessionStartTime, Integer customerID) {
        String query = "UPDATE chargers SET status = ?, sessionStartTime = ?, currentUserId = ? WHERE chargerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, status);
            pstmt.setTimestamp(2, sessionStartTime != null ? Timestamp.valueOf(sessionStartTime) : null);
            if (customerID == null) {
                pstmt.setNull(3, Types.INTEGER);
            } else {
                pstmt.setInt(3, customerID);
            }
            pstmt.setInt(4, chargerID);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }




    // Update chargingTransactions record after the session has ended
    public void updateChargingTransaction(int transactionID, LocalDateTime endTime, BigDecimal energyConsumed, BigDecimal totalCost) {
        String query = "UPDATE chargingTransactions SET endTime = ?, energyConsumed = ?, totalCost = ? WHERE transactionID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setTimestamp(1, Timestamp.valueOf(endTime));
            pstmt.setBigDecimal(2, energyConsumed);
            pstmt.setBigDecimal(3, totalCost);
            pstmt.setInt(4, transactionID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Fetch the start time and charger ID for a transaction
    public LocalDateTime fetchStartTimeForTransaction(int transactionID) {
        String query = "SELECT startTime FROM chargingTransactions WHERE transactionID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, transactionID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getTimestamp("startTime").toLocalDateTime();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Consider proper error handling
    }

    // Calculate the duration of charging session needed for calculating the cost
    public BigDecimal calculateDurationHours(LocalDateTime startTime, LocalDateTime endTime) {
        long seconds = Duration.between(startTime, endTime).getSeconds();
        return BigDecimal.valueOf(seconds).divide(BigDecimal.valueOf(3600), 2, RoundingMode.HALF_UP);
    }


    public BigDecimal calculateEnergyConsumed(BigDecimal durationHours, int chargerID) {
        BigDecimal kw = fetchChargerKw(chargerID); // Method to fetch kW rating of the charger
        return durationHours.multiply(kw);
    }

    public BigDecimal calculateTotalCost(BigDecimal energyConsumed, int chargerID) {
        BigDecimal costPerKWH = fetchChargerCostPerKWH(chargerID); // Method to fetch cost per kWh for the charger
        return energyConsumed.multiply(costPerKWH);
    }

    // Helper methods to fetch kW and costPerKWH based on chargerID
    public BigDecimal fetchChargerKw(int chargerID) {
        String query = "SELECT kw FROM chargers WHERE chargerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, chargerID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return BigDecimal.valueOf(rs.getInt("kw"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO; // Default value if not found
    }

    public BigDecimal fetchChargerCostPerKWH(int chargerID) {
        String query = "SELECT costPerKWH FROM chargers WHERE chargerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, chargerID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal("costPerKWH");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO; // Default value if not found
    }


    public int startChargingSession(int chargerID, int customerID) {
        LocalDateTime sessionStartTime = LocalDateTime.now();
        BigDecimal rate = fetchChargerCostPerKWH(chargerID);  // Make sure this method is accessible

        if (updateChargerStatus(chargerID, "In-Use", sessionStartTime, customerID)) {
            return createChargingTransaction(sessionStartTime, chargerID, customerID, rate);
        }
        return -1;  // Indicate failure if the charger status couldn't be updated
    }

    public int createChargingTransaction(LocalDateTime sessionStartTime, int chargerID, int customerID, BigDecimal rate) {
        String sql = "INSERT INTO chargingTransactions (startTime, chargerID, customerID, rate) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setTimestamp(1, Timestamp.valueOf(sessionStartTime));
            pstmt.setInt(2, chargerID);
            pstmt.setInt(3, customerID);
            pstmt.setBigDecimal(4, rate);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating transaction failed, no rows affected.");
            }
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);  // Return the new transaction ID
                } else {
                    throw new SQLException("Creating transaction failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;  // Return -1 if transaction creation fails
    }



    /////

}//END CLASS*/



/*public int startChargingSession(int chargerID, int customerID) {
        LocalDateTime sessionStartTime = LocalDateTime.now();
        // Start transaction in the database and mark charger as in use
        if (updateChargerStatus(chargerID, "In-Use", sessionStartTime, customerID)) {
            String sql = "INSERT INTO chargingTransactions (startTime, chargerID, customerID) VALUES (?, ?, ?)";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setTimestamp(1, Timestamp.valueOf(sessionStartTime));
                pstmt.setInt(2, chargerID);
                pstmt.setInt(3, customerID);
                pstmt.executeUpdate();
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);  // Return transaction ID
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1;  // Return invalid transaction ID if failure
    }*/



   /* public int createChargingTransaction(LocalDateTime sessionStartTime, int chargerID, int customerID, BigDecimal rate) {
        String sql = "INSERT INTO chargingTransactions (startTime, chargerID, customerID, rate) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setTimestamp(1, Timestamp.valueOf(sessionStartTime));
            pstmt.setInt(2, chargerID);
            pstmt.setInt(3, customerID);
            pstmt.setBigDecimal(4, rate);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating transaction failed, no rows affected.");
            }
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);  // Return the new transaction ID
                } else {
                    throw new SQLException("Creating transaction failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;  // Return -1 if transaction creation fails
    }*/
///
// Adjustments in ChargingStationModel