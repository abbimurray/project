//Student number:C00260073, Student name: Abigail Murray, Semester two


package model;

//imports from my other packages
import utils.DBConnection;
//imports
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.Duration;

public class ChargingStationModel {


    //--------------------------methods used in FindChargingStations----------------------------------------

    // Get distinct counties method used in the FindChargingStations class for populating counties
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
    }//end

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
    }//end

    //------------------------------------methods used in StationDetails Class---------------------------

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
    }//end


    ///---------------------methods used in StartSessionForm
    // Check charger availability before initiate a charging session
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
    }//end


    // Update the status of the charger - used for starting and ending a session
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
    }//end


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
    }//end

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
        return null;
    }//end


    // Calculate the duration of charging session, needed for calculating total cost in endSession
    public BigDecimal calculateDurationHours(LocalDateTime startTime, LocalDateTime endTime) {
        long seconds = Duration.between(startTime, endTime).getSeconds();
        return BigDecimal.valueOf(seconds).divide(BigDecimal.valueOf(3600), 2, RoundingMode.HALF_UP);
    }//end

    //Calculate the energy consumed, needed for calculating the total cost in endSession
    //energy consumed = kW * duration in hours
    public BigDecimal calculateEnergyConsumed(BigDecimal durationHours, int chargerID) {
        BigDecimal kw = fetchChargerKw(chargerID); // Method to fetch kW rating of the charger
        return durationHours.multiply(kw);
    }//end

    //calcualte the total cost of the charging session, used in endSession
    //totalcost= energyConsumed * costPerKwh
    public BigDecimal calculateTotalCost(BigDecimal energyConsumed, int chargerID) {
        BigDecimal costPerKWH = fetchChargerCostPerKWH(chargerID); // Method to fetch cost per kWh for the charger
        return energyConsumed.multiply(costPerKWH);
    }//end

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
    }//end


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
    }//end



    //method used to create a charging transaction
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
    }//end


    //-------------------------method(s) used for viewChargingTransactions--------------
    public List<ChargingTransaction> getTransactionsForCustomer(int customerID) {
        List<ChargingTransaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM chargingTransactions WHERE customerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, customerID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                transactions.add(new ChargingTransaction(
                        rs.getInt("transactionID"),
                        rs.getTimestamp("startTime").toLocalDateTime(),
                        rs.getTimestamp("endTime") != null ? rs.getTimestamp("endTime").toLocalDateTime() : null,
                        rs.getBigDecimal("energyConsumed"),
                        rs.getBigDecimal("totalCost"),
                        rs.getInt("chargerID"),
                        customerID
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }//end



}// end ChargingStationModel