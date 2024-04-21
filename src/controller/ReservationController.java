package controller;

import dao.ReservationDao;
import model.Reservation;
import java.util.List;

public class ReservationController {
    private ReservationDao reservationDao;

    public ReservationController() {
        this.reservationDao = new ReservationDao();
    }

    // Get reservations for a specific customer

    public List<Reservation> getReservationsForCustomer(int customerID) {
        return reservationDao.getReservationsByCustomerId(customerID);
    }
    // Add a new reservation
    public boolean addReservation(Reservation reservation) {
        if (!reservationDao.isChargerAvailable(reservation.getChargerID(), reservation.getReservationStartTime(), reservation.getReservationEndTime())) {
            System.out.println("Charger is not available at the requested time.");
            return false;
        }
        if (reservationDao.addReservation(reservation)) {
            return reservationDao.updateChargerStatus(reservation.getChargerID(), "Reserved");
        }
        return false;
    }

    // Delete an existing reservation
    public boolean deleteReservation(int reservationID) {
        int chargerID = reservationDao.getChargerIDForReservation(reservationID);
        if (chargerID == -1) {
            System.out.println("Charger ID for the given reservation not found.");
            return false;
        }
        if (reservationDao.deleteReservation(reservationID)) {
            reservationDao.updateChargerStatus(chargerID, "Available");
            return true;
        }
        System.out.println("Failed to delete the reservation.");
        return false;
    }

    // Update an existing reservation
    public boolean updateReservation(Reservation reservation) {
        if (!reservationDao.isChargerAvailable(reservation.getChargerID(), reservation.getReservationStartTime(), reservation.getReservationEndTime())) {
            System.out.println("Charger is not available at the requested new time.");
            return false;
        }
        return reservationDao.updateReservation(reservation);
    }
}

/*package controller;

import dao.ReservationDao;
import model.Reservation;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ReservationController {
    private ReservationDao reservationDao;

    public ReservationController() {
        this.reservationDao = new ReservationDao();
    }

    public List<Reservation> getReservationsForCustomer(int customerID) {
        return reservationDao.getReservationsByCustomerId(customerID);
    }



    public boolean addReservation(Reservation reservation) {
        if (!reservationDao.isChargerAvailable(reservation.getChargerID(), reservation.getReservationStartTime(), reservation.getReservationEndTime())) {
            System.out.println("Charger is not available at the requested time.");
            return false;
        }
        if (reservationDao.addReservation(reservation)) {
            return reservationDao.updateChargerStatus(reservation.getChargerID(), "Reserved");
        }
        return false;
    }

    public boolean deleteReservation(int reservationID) {
        int chargerID = reservationDao.getChargerIDForReservation(reservationID);
        if (chargerID == -1) {
            System.out.println("Charger ID for the given reservation not found.");
            return false;
        }
        if (reservationDao.deleteReservation(reservationID)) {
            return reservationDao.updateChargerStatus(chargerID, "Available");
        }
        System.out.println("Failed to delete the reservation.");
        return false;
    }


}
*/

/*package controller;

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

    //when adding a new reservation change the status of the charger status
    /*public boolean addReservation(Reservation reservation) {
        boolean reservationAdded = reservationDAO.addReservation(reservation);
        if (reservationAdded) {
            return updateChargerStatus(reservation.getChargerID(), "Reserved");
        }
        return false;
    }
    public boolean addReservation(Reservation reservation) {
        // Check if the charger is available for the proposed reservation time
        if (!reservationDao.isChargerAvailable(reservation.getChargerID(), reservation.getReservationDateTime(), 60)) { // Assuming a 1-hour block for simplicity
            System.out.println("Charger is not available at the requested time.");
            return false;
        }

        // If the charger is available, proceed to add the reservation
        boolean reservationAdded = reservationDAO.addReservation(reservation);
        return reservationAdded;
    }


    public boolean deleteReservation(int reservationID) {
        // Fetch the charger ID before attempting to delete the reservation
        int chargerID = reservationDAO.getChargerIDForReservation(reservationID);
        if (chargerID == -1) {//chargerid not found
            System.out.println("Charger ID for the given reservation not found.");
            return false;
        }

        //  delete the reservation
        boolean reservationDeleted = reservationDAO.deleteReservation(reservationID);
        if (reservationDeleted) {
            // If the reservation was successfully deleted, update the charger's status to "Available"
            return updateChargerStatus(chargerID, "Available");
        } else {
            System.out.println("Failed to delete the reservation.");
            return false;
        }
    }




    //used when you add or cancel or even update a reservation
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

}*/