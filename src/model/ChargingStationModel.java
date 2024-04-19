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

}//END CLASS



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