//Student number:C00260073, Student name: Abigail Murray, Semester two

package controller;

import dao.ReservationDao;
import model.Reservation;
import java.util.List;

public class ReservationController {
    private ReservationDao reservationDao;

    public ReservationController() {
        this.reservationDao = new ReservationDao();
    }

    // Get reservations for a specific customer - customer id

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

}// end class