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

    /**
     * Retrieves a list of unique counties from the charging stations database. This method queries the database
     * to find all distinct counties where charging stations are located and returns them in alphabetical order.
     * It is for filtering charging stations by county.
     *
     * @return a list of strings, each representing a unique county name where charging stations are located;
     *         the list is sorted alphabetically and will be empty if no counties are found or if an error occurs during database access
     */
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

    /**
     * Retrieves a list of charging stations located in a given county. This method queries the database
     * for all charging stations that match the given county and constructs a list of ChargingStation objects
     * from the results. Each ChargingStation object includes details such as station ID, county, address,
     * and the number of chargers at that station.
     *
     * @param county the name of the county to search for charging stations
     * @return a list of ChargingStation objects that are located in the specified county; this list will be
     *         empty if no stations are found in that county or if an error occurs during database access
     */
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

    /**
     * Retrieves a list of chargers located at a specified station. This method queries the database
     * to find all chargers associated with the given station ID and constructs a list of Charger objects
     * from the retrieved data.
     *
     * @param stationId the ID of the station for which chargers are to be retrieved
     * @return a list of Charger objects for the specified station; this list will be empty if no chargers are found
     *         or if an error occurs during the database access
     */
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

    /**
     * Checks if a specific charger is available for use.
     * This method queries the charger's status from the database and determines if it is marked as "Available".
     *
     * @param chargerID the ID of the charger whose availability is to be checked
     * @return true if the charger is available, false if the charger is not available or if any errors occur during database access
     */
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

    /**
     * Updates the status, session start time, and current user of a charger based on the chargerID.
     * @param chargerID the ID of the charger to update
     * @param status the new status to set for the charger
     * @param sessionStartTime the start time of the charger session, can be null
     * @param customerID the ID of the customer using the charger, can be null if no user is associated
     * @return true if the update was successful and affected at least one row, false otherwise
     */
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

    /**
     * Updates the details of an existing charging transaction in the database.
     *
     * @param transactionID the unique identifier of the charging transaction to be updated
     * @param endTime the end time of the charging session
     * @param energyConsumed the total amount of energy consumed during the charging session, measured in kWh
     * @param totalCost the total cost incurred for the charging session
     */
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


    // Fetch the start time for a transaction
    /**
     * Fetches the start time of a charging transaction from the database based on the transaction ID provided.
     * This method queries the database for the start time of a specific transaction and returns it as a LocalDateTime object.
     *
     * @param transactionID  of the charging transaction for which the start time is to be fetched
     * @return the start time of the transaction as a LocalDateTime object; returns null if the transaction cannot be found or if there is a database access error
     */
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

    /**
     * Calculates the Duartion in hours of a charging station.
     * @param startTime
     * @param endTime
     * @return the duration in hours in which the charging transaction lasted
     */
    public BigDecimal calculateDurationHours(LocalDateTime startTime, LocalDateTime endTime) {
        long seconds = Duration.between(startTime, endTime).getSeconds();
        return BigDecimal.valueOf(seconds).divide(BigDecimal.valueOf(3600), 2, RoundingMode.HALF_UP);
    }//end

    //Calculate the energy consumed, needed for calculating the total cost in endSession
    //energy consumed = kW * duration in hours

    /**
     * Caluclates the energy consumed per charging transaction based off the duration in hours and kw of the charger
     *
     * @param durationHours
     * @param chargerID
     * @return energyConsumed
     */
    public BigDecimal calculateEnergyConsumed(BigDecimal durationHours, int chargerID) {
        BigDecimal kw = fetchChargerKw(chargerID); // Method to fetch kW rating of the charger
        return durationHours.multiply(kw);
    }//end

    //calcualte the total cost of the charging session, used in endSession
    //totalcost= energyConsumed * costPerKwh

    /**
     * Calculates the total cost of the charging session based off the energyconsumed and the cost per kwh provided
     * @param energyConsumed
     * @param chargerID
     * @return totalCost of the charging session
     */
    public BigDecimal calculateTotalCost(BigDecimal energyConsumed, int chargerID) {
        BigDecimal costPerKWH = fetchChargerCostPerKWH(chargerID); // Method to fetch cost per kWh for the charger
        return energyConsumed.multiply(costPerKWH);
    }//end


    // Helper methods to fetch kW and costPerKWH based on chargerID

    /**
     * Fetches the kw from a given charger
     * @param chargerID
     * @return kw from the specified charger
     */
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


    /**
     * Fetches the cost per kwh of a given charger
     * @param chargerID
     * @return cost per kwh
     */
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

    /**
     * Creates a charging session if all the parameters are supplied.
     * @param startTime
     * @param chargerID
     * @param customerID
     * @param rate
     * @return returns -1 if it was unsuccessful and a transaction was not created
     */
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

    /**
     * Retrieves all transactions a specified customer has
     * @param customerID
     * @return transactions
     */
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



}// end class