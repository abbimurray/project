package GUI;

import java.sql.Date;
import java.time.format.DateTimeFormatter;

public class Reservation {
    private int reservationID;

    private DateTimeFormatter reservationDateTime;
    private String status;
    private int userID;
    private int stationID;

    public Reservation(){

    }

    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public DateTimeFormatter getReservationDateTime() {
        return reservationDateTime;
    }

    public void setReservationDateTime(DateTimeFormatter reservationDateTime) {
        this.reservationDateTime = reservationDateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getStationID() {
        return stationID;
    }

    public void setStationID(int stationID) {
        this.stationID = stationID;
    }

    public Reservation(int reservationID, DateTimeFormatter reservationDateTime, String status, int userID, int stationID)
    {
        this.reservationID= reservationID;
        this.reservationDateTime= reservationDateTime;
        this.status= status;
        this.userID= userID;
        this.stationID = stationID;
    }
}
