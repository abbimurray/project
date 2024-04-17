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

    // You might also want to implement reserveCharger method here
}