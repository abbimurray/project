/*package controller;

import model.ChargingStationModel;
import java.time.LocalDateTime;
import java.math.BigDecimal;

public class ChargerController {
    private ChargingStationModel model;

    public ChargerController(ChargingStationModel model) {
        this.model = model;
    }


    public boolean startChargingSession(int chargerID, int customerID) {
        if (model.checkChargerAvailability(chargerID)) {
            LocalDateTime sessionStartTime = LocalDateTime.now();//set start time to now
            BigDecimal rate = model.fetchChargerCostPerKWH(chargerID); //get the rate

            int transactionID = model.createChargingTransaction(sessionStartTime, chargerID, customerID,rate);
            if (transactionID != -1) {
                if (model.updateChargerStatus(chargerID, "In-Use", sessionStartTime, customerID)) {
                    System.out.println("Session started for charger (chargercontroller)" + chargerID + " and customer " + customerID + " with transaction ID " + transactionID);
                    return true;
                }
            }
        }
        System.out.println("Session could not be started for charger " + chargerID);
        return false;
    }

    this method isnt actually being used
    public boolean endChargingSession(int chargerID, int transactionID) {
        LocalDateTime startTime = model.fetchStartTimeForTransaction(transactionID);
        LocalDateTime endTime = LocalDateTime.now();
        BigDecimal durationHours = model.calculateDurationHours(startTime, endTime);
        BigDecimal energyConsumed = model.calculateEnergyConsumed(durationHours, chargerID);
        BigDecimal totalCost = model.calculateTotalCost(energyConsumed, chargerID);
        model.updateChargingTransaction(transactionID, endTime, energyConsumed, totalCost);
        model.updateChargerStatus(chargerID, "Available", null, null);
        System.out.println("Session ended for charger(chargercontroller) " + chargerID + " with transaction ID " + transactionID);
        return true;
    }



package controller;

import model.ChargingStationModel;
import java.time.LocalDateTime;

public class ChargerController {
    private ChargingStationModel model;

    public ChargerController(ChargingStationModel model) {
        this.model = model;
    }

    // Adjusted to not redeclare itself
    public boolean startChargingSession(int chargerID, int customerID) {
        // Check if the charger is available
        if (model.checkChargerAvailability(chargerID)) {
            // Charger is available, proceed with starting the charging session
            // This involves updating the charger's status in the database to 'In-Use'
            boolean statusUpdated = model.updateChargerStatus(chargerID, "In-Use");
            if (statusUpdated) {
                // Assuming you have a method to record the session start (not shown)
                // For simplicity, let's say we just print a message for now
                System.out.println("Session started for charger " + chargerID + " and customer " + customerID);
                // You might want to insert a new session record into the database here
                return true; // Return true to indicate the session was successfully started
            }
        }
        // Charger is not available or status update failed
        System.out.println("Session could not be started for charger " + chargerID);
        return false; // Return false to indicate the session could not be started
    }


}*/
