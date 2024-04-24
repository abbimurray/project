package model;

import java.time.LocalDateTime;

public class Reservation {
    private int reservationID; // Unique identifier for the reservation
    private String status; // Status of the reservation (e.g., "Confirmed", "Cancelled")
    private int stationID; // Identifier for the charging station
    private int customerID; // Identifier for the customer who made the reservation
    private int chargerID; // Identifier for the charger at the station
    private LocalDateTime reservationStartTime; // Start time of the reservation
    private LocalDateTime reservationEndTime; // End time of the reservation

    // Constructor

    /**
     * Default constructor for Reservation
     */
    public Reservation() {
    }

    // Getters and setters

    /**
     * getter method for reservationID
     * @return reservationID
     */
    public int getReservationID() {
        return reservationID;
    }


    /**
     * setter method for reservationID
     * @param reservationID
     */
    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }


    /**
     * Getter method to get the status of the reservation
     * @return status of the reservation
     */
    public String getStatus() {
        return status;
    }

    /**
     * Setter method to set the status of the reservaton
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * getter method to get the stationID for the resevation
     * @return stationID
     */
    public int getStationID() {
        return stationID;
    }

    /**
     * SETTER method to set the stationID- RESERVATONS
     * @param stationID
     */
    public void setStationID(int stationID) {
        this.stationID = stationID;
    }

    /**
     * getter method to get the customerID - for reservations
     * @return customerID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * setter method to set the customerID - reservations
     * @param customerID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * getter method to return the chargerID - reservations
     * @return chargerID
     */
    public int getChargerID() {
        return chargerID;
    }

    /**
     * setter method to set chargerID - reservations
     * @param chargerID
     */
    public void setChargerID(int chargerID) {
        this.chargerID = chargerID;
    }


    /**
     * getter method to get the reservation start time
     * @return reservationStartTime
     */
    public LocalDateTime getReservationStartTime() {
        return reservationStartTime;
    }

    /**
     * setter method to set the reservation startTime
     * @param reservationStartTime
     */
    public void setReservationStartTime(LocalDateTime reservationStartTime) {
        this.reservationStartTime = reservationStartTime;
    }

    /**
     * getter method to return the reservationEndTime
     * @return reservationEndTime
     */

    public LocalDateTime getReservationEndTime() {
        return reservationEndTime;
    }

    /**
     * setter method to set the reservationEndTime
     * @param reservationEndTime
     */
    public void setReservationEndTime(LocalDateTime reservationEndTime) {
        this.reservationEndTime = reservationEndTime;
    }

    // For debugging purposes

    /**
     * to string method for reservations which returns details about the reservation
     * @return reservationID, stationID, chargerID, customerID, reservationStartTime, reservationEndTime
     */
    @Override
    public String toString() {
        return
                "reservationID=" + reservationID  +
                ", stationID=" + stationID +
                ", chargerID=" + chargerID +
                ", customerID=" + customerID +
                ", reservationStartTime=" + reservationStartTime +
                ", reservationEndTime=" + reservationEndTime;
    }

}//end class
