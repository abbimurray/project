package model;

public class ChargingStation {
    private int stationID;
    private String county;
    private String address;
    private int numberOfChargers;

    //constructor methods
    public ChargingStation() {
    }

    public ChargingStation(int stationID, String county, String address, int numberOfChargers) {
        this.stationID = stationID;
        this.county = county;
        this.address = address;
        this.numberOfChargers = numberOfChargers;
    }

    // Getters and setters

    //stationID
    public int getStationID() {
        return stationID;
    }

    public void setStationID(int stationID) {
        this.stationID = stationID;
    }

    //county
    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    //address
    public String getAddress() {
        return address;
    }

    public void setAddress(String address){
        this.address= address;
    }

    //numberOfChargers
    public int getNumberOfChargers() {
        return numberOfChargers;
    }

    public void setNumberOfChargers(int numberOfChargers){
        this.numberOfChargers= numberOfChargers;
    }

    @Override
    public String toString() {
        return county + " - " + address; // Customize this based on your class fields
    }

}//end class