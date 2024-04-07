package dao;

import model.Reservation;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Reservation> getReservationsByCustomerId(int customerID) {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservations WHERE customerID = ? ORDER BY reservationDateTime DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, customerID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println("Fetched data: " + rs.getInt("reservationID") + ", " + rs.getTimestamp("reservationDateTime") + rs.getString("status")+ rs.getInt("stationID") + rs.getInt("customerID") + rs.getInt("chargerID"));
                Reservation reservation = new Reservation();
                reservation.setReservationID(rs.getInt("reservationID"));
                reservation.setStationID(rs.getInt("stationID"));
                reservation.setChargerID(rs.getInt("chargerID")); // Make sure this is correctly set
                reservation.setCustomerID(rs.getInt("customerID")); // This seems redundant if you're querying by customerID
                reservation.setReservationDateTime(rs.getTimestamp("reservationDateTime").toLocalDateTime());
                reservation.setStatus(rs.getString("status"));

                reservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider more robust error handling or logging for production.
        }
        return reservations;
    }

    //CRUD RETRIEVE - View reservations


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
        String sql = "DELETE FROM reservations WHERE reservationID = ? AND reservationDateTime >= CURRENT_TIMESTAMP";
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

}//end class

