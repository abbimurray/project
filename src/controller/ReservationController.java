//Student number:C00260073, Student name: Abigail Murray, Semester two

package controller;

import dao.ReservationDao;
import model.Reservation;
import java.util.List;

public class ReservationController {
    private ReservationDao reservationDao;

    /**
     * Constructs a new ReservationController. This constructor initializes the ReservationDao
     * that this controller will use to interact with the database for reservation operations.
     * It sets up the necessary infrastructure for managing payment methods within the application.
     */
    public ReservationController() {
        this.reservationDao = new ReservationDao();
    }

    // Get reservations for a specific customer - customer id

    /**
     * Retrieve the reservations for the specified customer, based on the customerID of the customer logged in
     * @param customerID
     * @return reservations list of all reservations the customer has made
     */
    public List<Reservation> getReservationsForCustomer(int customerID) {
        return reservationDao.getReservationsByCustomerId(customerID);
    }


    // Add a new reservation

    /**
     * Add a new reservation
     * Checks if the charger is available during specified time
     * Update status of the charger to reserved if the reservation was successful
     * @param reservation the reservation object
     * @return true if reservation was successfully added and charger status was updated, false otherwise
     */
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

    /**
     * Delete a reservation based on the reservationID selected
     * @param reservationID
     * @return true if the reservation was successfully deleted, false otherwise
     */
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

    /**


    /**
     * Updates an existing reservation
     * Checks if the charger is available during the new specified time
     * If charger is available at new time, update the reservation details
     * @param reservation reservation object
     * @return true if the reservation could be updated, false otherwise
     */
    public boolean updateReservation(Reservation reservation) {
        if (!reservationDao.isChargerAvailable(reservation.getChargerID(), reservation.getReservationStartTime(), reservation.getReservationEndTime())) {
            System.out.println("Charger is not available at the requested new time.");
            return false;
        }
        return reservationDao.updateReservation(reservation);
    }

}// end class