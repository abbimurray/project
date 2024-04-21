
//Student number:C00260073, Student name: Abigail Murray, Semester two

package dao;

import model.Reservation;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationDao {

    // Add a new reservation
    public boolean addReservation(Reservation reservation) {
        String sql = "INSERT INTO reservations (reservationStartTime, reservationEndTime, status, stationID, customerID, chargerID) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setTimestamp(1, Timestamp.valueOf(reservation.getReservationStartTime()));
            pstmt.setTimestamp(2, Timestamp.valueOf(reservation.getReservationEndTime()));
            pstmt.setString(3, reservation.getStatus());
            pstmt.setInt(4, reservation.getStationID());
            pstmt.setInt(5, reservation.getCustomerID());
            pstmt.setInt(6, reservation.getChargerID());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retrieve all reservations for a specific customer
    public List<Reservation> getReservationsByCustomerId(int customerID) {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservations WHERE customerID = ? ORDER BY reservationStartTime DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, customerID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Reservation reservation = new Reservation();
                reservation.setReservationID(rs.getInt("reservationID"));
                reservation.setStationID(rs.getInt("stationID"));
                reservation.setChargerID(rs.getInt("chargerID"));
                reservation.setCustomerID(rs.getInt("customerID"));

                Timestamp startTimeStamp = rs.getTimestamp("reservationStartTime");
                Timestamp endTimeStamp = rs.getTimestamp("reservationEndTime");
                reservation.setReservationStartTime(startTimeStamp != null ? startTimeStamp.toLocalDateTime() : null);
                reservation.setReservationEndTime(endTimeStamp != null ? endTimeStamp.toLocalDateTime() : null);

                reservation.setStatus(rs.getString("status"));
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }


    // Update a reservation
    public boolean updateReservation(Reservation reservation) {
        String sql = "UPDATE reservations SET reservationStartTime = ?, reservationEndTime = ?, status = ? WHERE reservationID = ? AND reservationStartTime >= CURRENT_TIMESTAMP";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setTimestamp(1, Timestamp.valueOf(reservation.getReservationStartTime()));
            pstmt.setTimestamp(2, Timestamp.valueOf(reservation.getReservationEndTime()));
            pstmt.setString(3, reservation.getStatus());
            pstmt.setInt(4, reservation.getReservationID());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete a reservation
    public boolean deleteReservation(int reservationID) {
        String sql = "DELETE FROM reservations WHERE reservationID = ? AND reservationStartTime >= CURRENT_TIMESTAMP";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, reservationID);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Check if a charger is available
    public boolean isChargerAvailable(int chargerID, LocalDateTime startTime, LocalDateTime endTime) {
        String sql = "SELECT COUNT(*) FROM reservations WHERE chargerID = ? AND NOT (reservationEndTime <= ? OR reservationStartTime >= ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, chargerID);
            pstmt.setTimestamp(2, Timestamp.valueOf(startTime));
            pstmt.setTimestamp(3, Timestamp.valueOf(endTime));
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Update charger status
    public boolean updateChargerStatus(int chargerID, String status) {
        String sql = "UPDATE chargers SET status = ? WHERE chargerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, chargerID);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // This method retrieves the chargerID for a given reservationID
    public int getChargerIDForReservation(int reservationID) {
        String sql = "SELECT chargerID FROM reservations WHERE reservationID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, reservationID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("chargerID");  // Return the chargerID if found
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if not found or an error occurs
    }

}//end class