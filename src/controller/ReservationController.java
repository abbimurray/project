package controller;

import dao.ReservationDao;
import model.Reservation;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ReservationController {
    private DBConnection dbConnection;
    private final ReservationDao reservationDAO = new ReservationDao();

    private ReservationDao reservationDao;

    public ReservationController() {
        this.reservationDao = new ReservationDao();
    }

    public List<Reservation> getReservationsForCustomer(int customerID) {
        return reservationDao.getReservationsByCustomerId(customerID);
    }
    public boolean addReservation(Reservation reservation) {
        boolean reservationAdded = reservationDAO.addReservation(reservation);
        if (reservationAdded) {
            return updateChargerStatus(reservation.getChargerID(), "Reserved");
        }
        return false;
    }

    private boolean updateChargerStatus(int chargerID, String newStatus) {
        String updateQuery = "UPDATE chargers SET status = ? WHERE chargerID = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
            pstmt.setString(1, newStatus);
            pstmt.setInt(2, chargerID);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            // Log the exception or handle it as per your application's error handling policy
            return false;
        }
    }
    public boolean isChargerAvailableAtStation(int chargerID, int stationID) {
        boolean existsAtStation = false;
        boolean isAvailable = false;

        // Check if the charger exists at the given station and if it's available
        String existenceAndAvailabilityQuery = "SELECT status FROM chargers WHERE chargerID = ? AND stationID = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(existenceAndAvailabilityQuery)) {

            pstmt.setInt(1, chargerID);
            pstmt.setInt(2, stationID);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                existsAtStation = true;
                String status = rs.getString("status");
                isAvailable = "Available".equals(status);
            }

            if (!existsAtStation) {
                System.out.println("No charger ID matching this at this station.");
                return false; // The charger doesn't exist at this station.
            } else if (!isAvailable) {
                System.out.println("Charger is not available."); // It exists but is not available.
                return false;
            } else {
                return true; // Charger exists at this station and is available.
            }

        } catch (SQLException e) {
            e.printStackTrace(); // change to proper logging.
            return false; // An error occurred.
        }
    }

}
