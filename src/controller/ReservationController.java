//Student number:C00260073, Student name: Abigail Murray, Semester two

package controller;

import dao.ReservationDao;
import model.Reservation;
import java.util.List;
import utils.LoggerUtility;
import java.util.logging.Level;


public class ReservationController {
    private ReservationDao reservationDao;

    public ReservationController() {
        this.reservationDao = new ReservationDao();
    }

    // Get reservations for a specific customer - customer id
    public List<Reservation> getReservationsForCustomer(int customerID) {
        try {
            return reservationDao.getReservationsByCustomerId(customerID);
        } catch (Exception e) {
            LoggerUtility.log(Level.SEVERE, "Failed to retrieve reservations for customer ID: " + customerID, e);
            return null;
        }
    }


    //add new reservation
    public boolean addReservation(Reservation reservation) {
        try {
            if (!reservationDao.isChargerAvailable(reservation.getChargerID(), reservation.getReservationStartTime(), reservation.getReservationEndTime())) {
                LoggerUtility.log(Level.WARNING, "Charger is not available at the requested time.");
                return false;
            }
            if (reservationDao.addReservation(reservation)) {
                boolean updateStatus = reservationDao.updateChargerStatus(reservation.getChargerID(), "Reserved");
                LoggerUtility.log(Level.INFO, "Reservation added successfully and charger status updated.");
                return updateStatus;
            }
            return false;
        } catch (Exception e) {
            LoggerUtility.log(Level.SEVERE, "Error in adding reservation: " + e.getMessage(), e);
            return false;
        }
    }

    // Delete an existing reservation
    public boolean deleteReservation(int reservationID) {
        try {
            int chargerID = reservationDao.getChargerIDForReservation(reservationID);
            if (chargerID == -1) {
                LoggerUtility.log(Level.WARNING, "Charger ID for the given reservation not found.");
                return false;
            }
            if (reservationDao.deleteReservation(reservationID)) {
                boolean updateStatus = reservationDao.updateChargerStatus(chargerID, "Available");
                LoggerUtility.log(Level.INFO, "Reservation deleted successfully and charger status updated.");
                return updateStatus;
            }
            LoggerUtility.log(Level.WARNING, "Failed to delete the reservation.");
            return false;
        } catch (Exception e) {
            LoggerUtility.log(Level.SEVERE, "Error in deleting reservation: " + e.getMessage(), e);
            return false;
        }
    }

    public boolean updateReservation(Reservation reservation) {
        try {
            if (!reservationDao.isChargerAvailable(reservation.getChargerID(), reservation.getReservationStartTime(), reservation.getReservationEndTime())) {
                LoggerUtility.log(Level.WARNING, "Charger is not available at the requested new time.");
                return false;
            }
            boolean updateSuccess = reservationDao.updateReservation(reservation);
            if (updateSuccess) {
                LoggerUtility.log(Level.INFO, "Reservation updated successfully.");
            }
            return updateSuccess;
        } catch (Exception e) {
            LoggerUtility.log(Level.SEVERE, "Error in updating reservation: " + e.getMessage(), e);
            return false;
        }
    }

}// end class
