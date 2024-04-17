package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;



public class Charger {
    private int chargerID;
    private String chargerType;
    private int stationID;
    private String status;
    private int kw;
    private BigDecimal costPerKWH;

    //constructor methods
    public Charger() {
    }

    public Charger(int chargerID, String chargerType, int stationID, String status, int kw, BigDecimal costPerKWH) {
        this.chargerID = chargerID;
        this.chargerType = chargerType;
        this.stationID = stationID;
        this.status = status;
        this.kw = kw;
        this.costPerKWH = costPerKWH;

    }

    // Getters and setters
    //chargeriD
    public int getChargerID() {
        return chargerID;
    }

    public void setChargerID(int chargerID) {
        this.chargerID = chargerID;
    }

    //stationID
    public int getStationID() {
        return stationID;
    }

    public void setStationID(int stationID) {
        this.stationID = stationID;
    }

    //chargerType
    public String getChargerType() {
        return chargerType;
    }

    public void setChargerType(String chargerType) {
        this.chargerType = chargerType;
    }

    //status
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    //kw
    public int getKw() {
        return kw;
    }

    public void setKw(int kw) {
        this.kw = kw;
    }

    //cost
    public BigDecimal getCostPerKWH() {
        return costPerKWH;
    }

    public void setCostPerKWH(BigDecimal costPerKWH) {
        this.costPerKWH = costPerKWH;
    }
}