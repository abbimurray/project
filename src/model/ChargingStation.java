//Student number:C00260073, Student name: Abigail Murray, Semester two

package model;

public class ChargingStation {
    private int stationID;
    private String county;
    private String address;
    private int numberOfChargers;

    //constructor methods

    /**
     * this is the default constructor for charging station
     */
    public ChargingStation() {
    }

    /**
     *
     * @param stationID - ID of the charger station
     * @param county - the county teh station is located in
     * @param address - the address of the charger station
     * @param numberOfChargers - the number of chargers at the charging station
     */
    public ChargingStation(int stationID, String county, String address, int numberOfChargers) {
        this.stationID = stationID;
        this.county = county;
        this.address = address;
        this.numberOfChargers = numberOfChargers;
    }

    // Getters and setters

    //stationID

    /**
     *
     * @return - the stationID
     */
    public int getStationID() {
        return stationID;
    }

    /**
     *
     * @param stationID
     */

    public void setStationID(int stationID) {
        this.stationID = stationID;
    }

    //county

    /**
     *
     * @return county
     */
    public String getCounty() {
        return county;
    }

    /**
     *
     * @param county
     */
    public void setCounty(String county) {
        this.county = county;
    }

    //address

    /**
     *
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address
     */
    public void setAddress(String address){
        this.address= address;
    }

    /**
     *
     * @return numberOfChargers
     */
    //numberOfChargers
    public int getNumberOfChargers() {
        return numberOfChargers;
    }

    /**
     *
     * @param numberOfChargers
     */

    public void setNumberOfChargers(int numberOfChargers){
        this.numberOfChargers= numberOfChargers;
    }

    /**
     *
     * @return county and address of charger station
     */
    @Override
    public String toString() {
        return county + " - " + address;
    }

}//end class