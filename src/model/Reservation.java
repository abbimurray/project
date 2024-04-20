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
    public Reservation() {
    }

    // Getters and setters
    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStationID() {
        return stationID;
    }

    public void setStationID(int stationID) {
        this.stationID = stationID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getChargerID() {
        return chargerID;
    }

    public void setChargerID(int chargerID) {
        this.chargerID = chargerID;
    }

    public LocalDateTime getReservationStartTime() {
        return reservationStartTime;
    }

    public void setReservationStartTime(LocalDateTime reservationStartTime) {
        this.reservationStartTime = reservationStartTime;
    }

    public LocalDateTime getReservationEndTime() {
        return reservationEndTime;
    }

    public void setReservationEndTime(LocalDateTime reservationEndTime) {
        this.reservationEndTime = reservationEndTime;
    }

    // For debugging purposes
    @Override
    public String toString() {
        return "Reservation{" +
                "reservationID=" + reservationID +
                ", status='" + status + '\'' +
                ", stationID=" + stationID +
                ", chargerID=" + chargerID +
                ", customerID=" + customerID +
                ", reservationStartTime=" + reservationStartTime +
                ", reservationEndTime=" + reservationEndTime +
                '}';
    }
}
