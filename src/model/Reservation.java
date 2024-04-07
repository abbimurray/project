package model;

import java.time.LocalDateTime;

public class Reservation {
    private int reservationID; // Unique identifier for the reservation
    private LocalDateTime reservationDateTime; // Date and time of the reservation
    private String status; // Status of the reservation (e.g., "Confirmed", "Cancelled")
    private int stationID; // Identifier for the charging station
    private int customerID; // Identifier for the customer who made the reservation
    private int chargerID;

    // Constructor
    public Reservation() {
    }

    // Overloaded constructor for creating a new Reservation instance
    public Reservation(LocalDateTime reservationDateTime, String status, int stationID, int customerID, int chargerID) {
        this.reservationDateTime = reservationDateTime;
        this.status = status;
        this.stationID = stationID;
        this.customerID = customerID;
        this.chargerID = chargerID;
    }

    // Getters and setters
    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }


    public int getChargerID() {
        return chargerID;
    }

    public void setChargerID(int chargerID) {
        this.chargerID = chargerID;
    }
    public LocalDateTime getReservationDateTime() {
        return reservationDateTime;
    }

    public void setReservationDateTime(LocalDateTime reservationDateTime) {
        this.reservationDateTime = reservationDateTime;
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

    // toString method for debugging purposes
    @Override
    public String toString() {
        return "Reservation{" +
                "reservationID=" + reservationID +
                ", reservationDateTime=" + reservationDateTime +
                ", status='" + status + '\'' +
                ", stationID=" + stationID +
                ", customerID=" + customerID +
                ", chargerID=" + chargerID +
                '}';
    }
}
