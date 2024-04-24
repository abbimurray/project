//Student number:C00260073, Student name: Abigail Murray, Semester two


package model;

import java.math.BigDecimal;

public class Charger {
    private int chargerID;
    private String chargerType;
    private int stationID;
    private String status;
    private int kw;
    private BigDecimal costPerKWH;

    //constructor methods

    /**
     * This is the default constructor
     */
    public Charger() {
    }

    /**
     *
     * @param chargerID - ID of the charger-
     * @param chargerType -Type of charger (ccs, chademo)
     * @param stationID - the ID of the station
     * @param status - Status of the charger (available, in-use, reserved, Under-Repair)
     * @param kw - kW of the charger
     * @param costPerKWH - the cost per kwh
     */

    public Charger(int chargerID, String chargerType, int stationID, String status, int kw, BigDecimal costPerKWH) {
        this.chargerID = chargerID;
        this.chargerType = chargerType;
        this.stationID = stationID;
        this.status = status;
        this.kw = kw;
        this.costPerKWH = costPerKWH;

    }

    // Getters and setters
    //chargerID

    /**
     *
     * @return - returns the chargerID
     */
    public int getChargerID() {
        return chargerID;
    }

    /**
     *
     * @param chargerID sets ID of the charger
     */
    public void setChargerID(int chargerID) {
        this.chargerID = chargerID;
    }

    //stationID

    /**
     *
     * @return- returns the stationID
     */
    public int getStationID() {
        return stationID;
    }

    /**
     *
     * @param stationID- sets the stationID
     */
    public void setStationID(int stationID) {
        this.stationID = stationID;
    }

    //chargerType

    /**
     *
     * @return - returns the charger type
     */
    public String getChargerType() {
        return chargerType;
    }

    /**
     *
     * @param chargerType - sets the charger type
     */
    public void setChargerType(String chargerType) {
        this.chargerType = chargerType;
    }

    //status

    /**
     *
     * @return - returns the charger status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status - sets the status of the charger
     */
    public void setStatus(String status) {
        this.status = status;
    }

    //kw

    /**
     *
     * @return - returns the kw
     */
    public int getKw() {
        return kw;
    }


    /**
     *
     * @param kw - sets the kw
     */
    public void setKw(int kw) {
        this.kw = kw;
    }

    //cost

    /**
     *
     * @return - returns the cost per kwh of the charger
     */
    public BigDecimal getCostPerKWH() {
        return costPerKWH;
    }

    /**
     *
     * @param costPerKWH - sets the cost per kwh of the charger
     */
    public void setCostPerKWH(BigDecimal costPerKWH) {
        this.costPerKWH = costPerKWH;
    }

}//end  class