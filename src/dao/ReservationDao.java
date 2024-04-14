package dao;

import model.Reservation;
import utils.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReservationDao {

    //CRUD CREATE ----ADD RESERVATION
    public boolean addReservation(Reservation reservation) {
        String sql = "INSERT INTO reservations (reservationDateTime, status, stationID, customerID,chargerID) VALUES (?, ?, ?, ?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, reservation.getReservationDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            pstmt.setString(2, reservation.getStatus());
            pstmt.setInt(3, reservation.getStationID());
            pstmt.setInt(4, reservation.getCustomerID());
            pstmt.setInt(5, reservation.getChargerID());

            int affectedRows = pstmt.executeUpdate();//execute adding
            return affectedRows > 0;//return true if successfully added
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //CRUD RETRIEVE - View reservations
    public List<Reservation> getReservationsByCustomerId(int customerID) {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservations WHERE customerID = ? ORDER BY reservationDateTime DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, customerID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println("Fetched data: " + rs.getInt("reservationID") + ", " + rs.getTimestamp("reservationDateTime") + rs.getString("status") + rs.getInt("stationID") + rs.getInt("customerID") + rs.getInt("chargerID"));
                Reservation reservation = new Reservation();
                reservation.setReservationID(rs.getInt("reservationID"));
                reservation.setStationID(rs.getInt("stationID"));
                reservation.setChargerID(rs.getInt("chargerID"));
                reservation.setCustomerID(rs.getInt("customerID"));
                reservation.setReservationDateTime(rs.getTimestamp("reservationDateTime").toLocalDateTime());
                reservation.setStatus(rs.getString("status"));

                reservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }


    //CRUD UPDATE--- UPDATE RESERVATION (PRESENT OR FUTURE)
    public boolean updateReservation(Reservation reservation) {
        String sql = "UPDATE reservations SET reservationDateTime = ?, status = ?, stationID = ?, customerID = ? WHERE reservationID = ? AND reservationDateTime >= CURRENT_TIMESTAMP";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setTimestamp(1, java.sql.Timestamp.valueOf(reservation.getReservationDateTime()));
            pstmt.setString(2, reservation.getStatus());
            pstmt.setInt(3, reservation.getStationID());
            pstmt.setInt(4, reservation.getCustomerID());
            pstmt.setInt(5, reservation.getReservationID());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //CRUD DELETE -- DELETE RESERVATION (PRESENT DAY OR FUTURE)
    public boolean deleteReservation(int reservationID) {
        //  String sql = "DELETE FROM reservations WHERE reservationID = ? AND reservationDateTime >= CURRENT_TIMESTAMP";
        String sql = "DELETE FROM reservations WHERE reservationID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, reservationID);//set reservationID parameter

            int affectedRows = pstmt.executeUpdate();//execute delete
            return affectedRows > 0;//true is it was deleted successfully
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public int getChargerIDForReservation(int reservationID) {
        String sql = "SELECT chargerID FROM reservations WHERE reservationID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, reservationID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("chargerID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Indicates not found or error
    }


    public boolean isChargerAvailable(int chargerID, LocalDateTime proposedStart, int durationInMinutes) {
        LocalDateTime proposedEnd = proposedStart.plusMinutes(durationInMinutes);
        String sql = "SELECT COUNT(*) FROM reservations WHERE chargerID = ? AND " +
                "((reservationDateTime < ? AND ADDDATE(reservationDateTime, INTERVAL durationInMinutes MINUTE) > ?) " +
                "OR (reservationDateTime < ? AND ADDDATE(reservationDateTime, INTERVAL durationInMinutes MINUTE) > ?))";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, chargerID);
            pstmt.setTimestamp(2, Timestamp.valueOf(proposedStart));
            pstmt.setTimestamp(3, Timestamp.valueOf(proposedEnd));
            pstmt.setTimestamp(4, Timestamp.valueOf(proposedEnd));
            pstmt.setTimestamp(5, Timestamp.valueOf(proposedStart));
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count == 0; // True if no overlapping reservations
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Default to false for safety
    }


}//end  class